package com.timsimonhughes.appcore.views.pageIndicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.timsimonhughes.appcore.R
import com.timsimonhughes.appcore.views.cardStackView.CardStackLayoutManager

class PageIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val defaultActiveIndicatorColor = Color.parseColor("#ffffff")
    private val defaultInactiveIndicatorColor = Color.parseColor("#000000")
    private val defaultDistanceBetweenIndicators = 5
    private val defaultNumberOfIndicators = 3
    private val defaultIndicatorRadiusDp = 5

    private var activeIndicatorPaint: Paint? = null
    private var inactiveIndicatorPaint: Paint? = null

    enum class IndicatorShape { Circle, RoundedRect }

    private var indicatorRadiusInPx: Float = 0f
    private var distanceBetweenIndicatorsInPx: Float = 0f

    private var circleLocations: FloatArray? = null
    private var yLocation: Float? = null

    private var activeIndicatorColor: Int? = null
    private var inactiveIndicatorColor: Int? = null
    private var numberOfIndicators: Int = 2

    private var currentIndex: Int = 0

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PageIndicatorView)
            indicatorRadiusInPx = typedArray.getDimension(
                R.styleable.PageIndicatorView_circle_radius,
                dpToPx(context, defaultIndicatorRadiusDp.toFloat())
            )
            distanceBetweenIndicatorsInPx = typedArray.getDimension(
                R.styleable.PageIndicatorView_distance_between_circles,
                defaultDistanceBetweenIndicators.toFloat()
            )
            numberOfIndicators = typedArray.getInt(
                R.styleable.PageIndicatorView_number_of_circles,
                defaultNumberOfIndicators
            )
            activeIndicatorColor = typedArray.getColor(
                R.styleable.PageIndicatorView_active_circle_color,
                defaultActiveIndicatorColor
            )
            inactiveIndicatorColor = typedArray.getColor(
                R.styleable.PageIndicatorView_inactive_circle_color,
                defaultInactiveIndicatorColor
            )
            typedArray.recycle()
        }

        if (inactiveIndicatorPaint == null) {
            inactiveIndicatorPaint = Paint().apply {
                isAntiAlias = true
                color = inactiveIndicatorColor!!
                style = Paint.Style.FILL
            }
        }

        if (activeIndicatorPaint == null) {
            activeIndicatorPaint = Paint().apply {
                isAntiAlias = true
                color = activeIndicatorColor!!
                style = Paint.Style.FILL
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val isMatchParent = MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY
        measureIndicators(isMatchParent)

        val measuredWidth = if (numberOfIndicators > 0) measureWidth(widthMeasureSpec) else 0
        val measuredHeight = if (numberOfIndicators > 0) measureHeight(heightMeasureSpec) else 0

        yLocation = (measuredHeight / 2).toFloat()

        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    private fun measureHeight(measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        return if (specMode == MeasureSpec.EXACTLY) {
            specSize
        } else {
            (indicatorRadiusInPx * 2 + paddingTop + paddingBottom).toInt()
        }
    }

    private fun measureWidth(measureSpec: Int) : Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        return if (specMode == MeasureSpec.EXACTLY) {
            specSize
        } else {
            (circleLocations!![numberOfIndicators - 1] + indicatorRadiusInPx + paddingRight).toInt()
        }
    }

    private fun measureIndicators(isMatchParent: Boolean) {
        if (numberOfIndicators > 0 && circleLocations == null) {

            circleLocations = FloatArray(numberOfIndicators)

            var xCounter = paddingLeft + indicatorRadiusInPx
            val spaceBetweenIndicators = indicatorRadiusInPx * 2 + distanceBetweenIndicatorsInPx

            for (i in 0 until numberOfIndicators) {
                circleLocations!![i] = xCounter
                xCounter += spaceBetweenIndicators
            }

            if (isMatchParent) {
                val screenWidth = context.resources.displayMetrics.widthPixels
                val widthOfView =
                    (circleLocations!![numberOfIndicators - 1] + indicatorRadiusInPx + paddingRight)
                val spaceToAddFromEachSide = (screenWidth - widthOfView) / 2

                for (i in circleLocations!!.indices) {
                    circleLocations!![i] += spaceToAddFromEachSide
                }
            }
        }
    }

    override fun requestLayout() {
        circleLocations = null
        super.requestLayout()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (circleLocations != null) {
            for (i in 0 until numberOfIndicators) {
                if (i == currentIndex) {
                    canvas?.drawCircle(
                        circleLocations!![i],
                        yLocation!!,
                        indicatorRadiusInPx,
                        activeIndicatorPaint!!
                    )
                } else {
                    canvas?.drawCircle(
                        circleLocations!![i],
                        yLocation!!,
                        indicatorRadiusInPx,
                        inactiveIndicatorPaint!!
                    )
                }
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val savedState = SavedState(superState)
        savedState.index = this.currentIndex

        // Return our state along with our super class's state.
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        // Cast the incoming Parcelable to our custom SavedState. We produced
        // this Parcelable before, so we know what type it is.

        val savedState = state as SavedState

        // Let our super class process state before we do because we should
        // depend on our super class, we shouldn't imply that our super class
        // might need to depend on us.

        super.onRestoreInstanceState(savedState.superState)

        // Grab our properties out of our SavedState.

        this.currentIndex = savedState.index
        // Update our visuals in whatever way we want, like...

        requestLayout()

        invalidate()
    }

    class SavedState : BaseSavedState {
        var index = 0

        constructor(superState: Parcelable?) : super(superState)

        constructor(source: Parcel) : super(source) {
            index = source.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(index)
        }

        val CREATOR = object : Parcelable.Creator<SavedState> {
            override fun createFromParcel(source: Parcel): SavedState {
                return SavedState(source)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }

    /**
     * Helper methods
     **/

    private fun dpToPx(context: Context, value: Float): Float {
        return value * context.resources.displayMetrics.density
    }

    /**
     * Get/Set methods
     **/

    fun getCurrentIndex(index: Int) {
        this.currentIndex = index
        invalidate()
    }

    fun getNumberOfIndicators(): Int {
        return numberOfIndicators
    }

    fun setNumberOfIndicators(indicatorCount: Int) {
        this.numberOfIndicators = indicatorCount
    }

    fun setCircleRadius(indicatorRadius: Float) {
        this.indicatorRadiusInPx = dpToPx(context, indicatorRadius)
    }

    fun setDistanceBetweenIndicators(distanceBetweenIndicators: Float) {
        this.distanceBetweenIndicatorsInPx = dpToPx(context, distanceBetweenIndicators)
    }

    fun setActiveIndicatorColor(activeColor: Int) {
        this.activeIndicatorColor = activeColor
    }

    fun setInactiveIndicatorColor(inactiveColor: Int) {
        this.inactiveIndicatorColor = inactiveColor
    }

    fun setupWithViewPager(viewpager: ViewPager2) {
        this.currentIndex = viewpager.currentItem
        invalidate()
    }

    fun setupWithRecyclerView(layoutManager: CardStackLayoutManager) {
        this.numberOfIndicators = layoutManager.itemCount
        this.currentIndex = layoutManager.getTopPosition()
        invalidate()
    }

    fun setCurrentIndex(index: Int) {
        this.currentIndex = index
        invalidate()
    }
}