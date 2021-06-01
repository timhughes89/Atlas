package com.timsimonhughes.appcore.views.cardStackView

import android.content.Context
import android.graphics.PointF
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import androidx.annotation.FloatRange
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.timsimonhughes.appcore.views.cardStackView.config.Direction
import com.timsimonhughes.appcore.views.cardStackView.config.SwipeableMethod
import com.timsimonhughes.appcore.views.cardStackView.internal.CardStackSetting
import com.timsimonhughes.appcore.views.cardStackView.internal.CardStackSmoothScroller
import com.timsimonhughes.appcore.views.cardStackView.internal.CardStackState
import com.timsimonhughes.appcore.views.cardStackView.internal.DisplayUtil


class CardStackLayoutManager(
    private val context: Context,
    private val cardStackListener: CardStackListener? = null
) : LinearLayoutManager(context),
    RecyclerView.SmoothScroller.ScrollVectorProvider {

    enum class StackFrom { None, Top, Bottom }
    private var stackFrom = StackFrom.Bottom

    private val state: CardStackState = CardStackState()
    private val setting: CardStackSetting = CardStackSetting()

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: Recycler?, s: RecyclerView.State) {
        update(recycler)
        if (s.didStructureChange()) {
            val topView = getTopView()
            if (topView != null) {
                cardStackListener!!.onCardAppeared(getTopView(), state.topPosition)
            }
        }
    }

    override fun canScrollHorizontally(): Boolean {
        return setting.swipeableMethod.canSwipe() && setting.canScrollHorizontal
    }

    override fun canScrollVertically(): Boolean {
        return setting.swipeableMethod.canSwipe() && setting.canScrollVertical;
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler?, s: RecyclerView.State?): Int {
        if (state.topPosition == itemCount) {
            return 0
        }
        when (state.status) {
            CardStackState.Status.Idle -> if (setting.swipeableMethod.canSwipeManually()) {
                state.dx -= dx
                update(recycler)
                return dx
            }
            CardStackState.Status.Dragging -> if (setting.swipeableMethod.canSwipeManually()) {
                state.dx -= dx
                update(recycler)
                return dx
            }
            CardStackState.Status.RewindAnimating -> {
                state.dx -= dx
                update(recycler)
                return dx
            }
            CardStackState.Status.AutomaticSwipeAnimating -> if (setting.swipeableMethod.canSwipeAutomatically()) {
                state.dx -= dx
                update(recycler)
                return dx
            }
            CardStackState.Status.AutomaticSwipeAnimated -> {
            }
            CardStackState.Status.ManualSwipeAnimating -> if (setting.swipeableMethod.canSwipeManually()) {
                state.dx -= dx
                update(recycler)
                return dx
            }
            CardStackState.Status.ManualSwipeAnimated -> {
            }
        }
        return 0
    }

    override fun scrollVerticallyBy(dy: Int, recycler: Recycler?, s: RecyclerView.State?): Int {
        if (state.topPosition == itemCount) {
            return 0
        }
        when (state.status) {
            CardStackState.Status.Idle -> if (setting.swipeableMethod.canSwipeManually()) {
                state.dy -= dy
                update(recycler)
                return dy
            }
            CardStackState.Status.Dragging -> if (setting.swipeableMethod.canSwipeManually()) {
                state.dy -= dy
                update(recycler)
                return dy
            }
            CardStackState.Status.RewindAnimating -> {
                state.dy -= dy
                update(recycler)
                return dy
            }
            CardStackState.Status.AutomaticSwipeAnimating -> if (setting.swipeableMethod.canSwipeAutomatically()) {
                state.dy -= dy
                update(recycler)
                return dy
            }
            CardStackState.Status.AutomaticSwipeAnimated -> {
            }
            CardStackState.Status.ManualSwipeAnimating -> if (setting.swipeableMethod.canSwipeManually()) {
                state.dy -= dy
                update(recycler)
                return dy
            }
            CardStackState.Status.ManualSwipeAnimated -> {
            }
        }
        return 0
    }

    override fun onScrollStateChanged(s: Int) {
        when (s) {
            RecyclerView.SCROLL_STATE_IDLE -> if (state.targetPosition == RecyclerView.NO_POSITION) {
                state.next(CardStackState.Status.Idle)
                state.targetPosition = RecyclerView.NO_POSITION
            } else if (state.topPosition == state.targetPosition) {
                state.next(CardStackState.Status.Idle)
                state.targetPosition = RecyclerView.NO_POSITION
            } else {
                if (state.topPosition < state.targetPosition) {
                    smoothScrollToNext(state.targetPosition)
                } else {
                    smoothScrollToPrevious(state.targetPosition)
                }
            }
            RecyclerView.SCROLL_STATE_DRAGGING -> if (setting.swipeableMethod.canSwipeManually()) {
                state.next(CardStackState.Status.Dragging)
            }
            RecyclerView.SCROLL_STATE_SETTLING -> {
            }
        }
    }

    override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
        return null
    }

    override fun scrollToPosition(position: Int) {
        if (setting.swipeableMethod.canSwipeAutomatically()) {
            if (state.canScrollToPosition(position, itemCount)) {
                state.topPosition = position
                requestLayout()
            }
        }
    }

    override fun smoothScrollToPosition(recyclerView: RecyclerView?, s: RecyclerView.State?, position: Int) {
        if (setting.swipeableMethod.canSwipeAutomatically()) {
            if (state.canScrollToPosition(position, itemCount)) {
                smoothScrollToPosition(position)
            }
        }
    }

    fun updateProportion(x: Float, y: Float) {
        if (getTopPosition() < itemCount) {
            val view = findViewByPosition(getTopPosition())
            if (view != null) {
                val half = height / 2.0f
                state.proportion = -(y - half - view.top) / half
            }
        }
    }

    private fun update(recycler: RecyclerView.Recycler?) {
        state.width = width
        state.height = height

        if (state.isSwipeCompleted) {
            removeAndRecycleView(getTopView()!!, recycler!!)
            val direction = state.direction
            state.next(state.status.toAnimatedStatus())
            state.topPosition++
            state.dx = 0
            state.dy = 0
            if (state.topPosition == state.targetPosition) {
                state.targetPosition = RecyclerView.NO_POSITION
            }

            Handler().post {
                cardStackListener!!.onCardSwiped(direction)
                val topView = getTopView()
                if (topView != null) {
                    cardStackListener.onCardAppeared(getTopView(), state.topPosition)
                }
            }
        }

        detachAndScrapAttachedViews(recycler!!)
        val parentTop = paddingTop
        val parentLeft = paddingLeft
        val parentRight = width - paddingLeft
        val parentBottom = height - paddingBottom
        var i = state.topPosition
        while (i < state.topPosition + setting.visibleCount && i < itemCount) {
            val child = recycler.getViewForPosition(i)
            addView(child, 0)
            measureChildWithMargins(child, 0, 0)
            layoutDecoratedWithMargins(child, parentLeft, parentTop, parentRight, parentBottom)

            resetTranslation(child)
            resetScale(child)
            resetRotation(child)

            if (i == state.topPosition) {
                val currentIndex = i - state.topPosition
                updateTranslation(child)
                resetScale(child)
                updateRotation(child)
//                updateAlpha(child)
            } else {
                val currentIndex = i - state.topPosition
                updateTranslation(child, currentIndex)
                updateScale(child, currentIndex)
                resetRotation(child)
//                resetAlpha(child)
            }
            i++
        }
        if (state.status.isDragging) {
            cardStackListener!!.onCardDragging(state.direction, state.ratio)
        }
    }

    private fun updateAlpha(view: View) {
        view.alpha = -state.dx.toFloat()
    }

    private fun resetAlpha(view: View) {
        view.alpha = 1.0f
    }

    /**
     * Translation
     **/
    private fun updateTranslation(view: View) {
        view.translationX = state.dx.toFloat()
        view.translationY = state.dy.toFloat()
    }

    private fun updateTranslation(view: View, index: Int) {
        val nextIndex = index - 1
        val translationPx = DisplayUtil.dpToPx(context, setting.translationInterval)
        val currentTranslation = index * translationPx.toFloat()
        val nextTranslation = nextIndex * translationPx.toFloat()
        val targetTranslation = currentTranslation - (currentTranslation - nextTranslation) * state.ratio
        when (stackFrom) {
            StackFrom.None -> {
                // Do nothing
            }
            StackFrom.Top -> {
                view.translationY = -targetTranslation
            }
            StackFrom.Bottom -> {
                view.translationY = targetTranslation
            }
        }
    }

    private fun resetTranslation(view: View) {
        view.translationX = 0.0f
        view.translationY = 0.0f
    }

    /**
     * Scale
     **/
    private fun updateScale(view: View, index: Int) {
        val nextIndex = index - 1
        val currentScale = 1.0f - index * (1.0f - setting.scaleInterval)
        val nextScale = 1.0f - nextIndex * (1.0f - setting.scaleInterval)
        val targetScale = currentScale + (nextScale - currentScale) * state.ratio
        when (stackFrom) {
            StackFrom.None -> {
                view.scaleX = targetScale
                view.scaleY = targetScale
            }
            StackFrom.Top -> {
                view.scaleX = targetScale
            }
            StackFrom.Bottom -> {
                view.scaleX = targetScale
            }
        }
    }

    private fun resetScale(view: View) {
        view.scaleX = 1.0f
        view.scaleY = 1.0f
    }

    /**
     * Rotation
     **/
    private fun updateRotation(view: View) {
        val degree = state.dx * setting.maxDegree / width * state.proportion
        view.rotation = degree
    }

    private fun resetRotation(view: View) {
        view.rotation = 0.0f
    }

    /**
     * Scroll to
     **/
    private fun smoothScrollToPosition(position: Int) {
        if (state.topPosition < position) {
            smoothScrollToNext(position)
        } else {
            smoothScrollToPrevious(position)
        }
    }

    private fun smoothScrollToNext(position: Int) {
        state.proportion = 0.0f
        state.targetPosition = position
        val scroller = CardStackSmoothScroller(CardStackSmoothScroller.ScrollType.AutomaticSwipe, this)
        scroller.targetPosition = state.topPosition
        startSmoothScroll(scroller)
    }

    private fun smoothScrollToPrevious(position: Int) {
        val topView = getTopView()
        if (topView != null) {
            cardStackListener?.onCardDisappeared(getTopView()!!, state.topPosition)
        }
        state.proportion = 0.0f
        state.targetPosition = position
        state.topPosition--
        val scroller = CardStackSmoothScroller(CardStackSmoothScroller.ScrollType.AutomaticRewind, this)
        scroller.targetPosition = state.topPosition
        startSmoothScroll(scroller)
    }

    /**
     * Methods for configuring layoutManager
     */

//    override fun onSaveInstanceState(): Parcelable {
//        val savedState = SavedState()
//        savedState.cardStackState = getTopPosition()
//        return savedState
//    }
//
//    override fun onRestoreInstanceState(state: Parcelable?) {
//        val savedState = state as SavedState
//        super.onRestoreInstanceState(savedState)
//        val savedStatePosition = savedState.cardStackState
//        this.state.topPosition = savedStatePosition
//    }
//
//    class SavedState : Parcelable {
//        var cardStackState = 0
//
//        constructor() {}
//        constructor(source: Parcel) {
//            cardStackState = source.readInt()
//        }
//
//        override fun describeContents(): Int {
//            return 0
//        }
//
//        override fun writeToParcel(dest: Parcel, flags: Int) {
//            dest.writeInt(cardStackState)
//        }
//
//        companion object {
//            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
//                override fun createFromParcel(parcelIn: Parcel): SavedState {
//                    return SavedState(parcelIn)
//                }
//
//                override fun newArray(size: Int): Array<SavedState?> {
//                    return arrayOfNulls(size)
//                }
//            }
//        }
//    }

    /**
     * Methods for configuring layoutManager
     */

    // Get methods
    fun getCardStackSetting(): CardStackSetting {
        return setting
    }

    fun getCardStackState(): CardStackState {
        return state
    }

    fun getCardStackListener(): CardStackListener? {
        return cardStackListener
    }

    fun getTopView(): View? {
        return findViewByPosition(state.topPosition)
    }

    fun getTopPosition(): Int {
        return state.topPosition
    }

    // Set methods
    fun setTopPosition(topPosition: Int) {
        state.topPosition = topPosition
    }

    fun setStackFrom(stackFrom: StackFrom) {
        this.stackFrom = stackFrom
    }

    fun setVisibleCount(visibleCount: Int) {
        require(visibleCount >= 1) { "VisibleCount must be greater than 0." }
        setting.visibleCount = visibleCount
    }

    fun setTranslationInterval(@FloatRange(from = 0.0) translationInterval: Float) {
        require(translationInterval >= 0.0f) { "TranslationInterval must be greater than or equal 0.0f" }
        setting.translationInterval = translationInterval
    }

    fun setScaleInterval(@FloatRange(from = 0.0) scaleInterval: Float) {
        require(scaleInterval >= 0.0f) { "ScaleInterval must be greater than or equal 0.0f." }
        setting.scaleInterval = scaleInterval
    }

    fun setSwipeThreshold(@FloatRange(from = 0.0, to = 1.0) swipeThreshold: Float) {
        require(!(swipeThreshold < 0.0f || 1.0f < swipeThreshold)) { "SwipeThreshold must be 0.0f to 1.0f." }
        setting.swipeThreshold = swipeThreshold
    }

    fun setMaxDegree(@FloatRange(from = (-360.0f).toDouble(), to = 360.0) maxDegree: Float) {
        require(!(maxDegree < -360.0f || 360.0f < maxDegree)) { "MaxDegree must be -360.0f to 360.0f" }
        setting.maxDegree = maxDegree
    }

    fun setDirections(directions: List<Direction>) {
        setting.directions = directions
    }

    fun setCanScrollHorizontal(canScrollHorizontal: Boolean) {
        setting.canScrollHorizontal = canScrollHorizontal
    }

    fun setCanScrollVertical(canScrollVertical: Boolean) {
        setting.canScrollVertical = canScrollVertical
    }

    fun setSwipeableMethod(swipeableMethod: SwipeableMethod) {
        setting.swipeableMethod = swipeableMethod
    }

    fun setSwipeAnimationSetting(swipeAnimationSetting: SwipeAnimationSetting) {
        setting.swipeAnimationSetting = swipeAnimationSetting
    }

    fun setRewindAnimationSetting(rewindAnimationSetting: RewindAnimationSetting) {
        setting.rewindAnimationSetting = rewindAnimationSetting
    }

    fun setOverlayInterpolator(overlayInterpolator: Interpolator) {
        setting.overlayInterpolator = overlayInterpolator
    }
}