package com.timsimonhughes.atlas.views;

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.timsimonhughes.atlas.R
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * A class that extends [AppCompatImageView] &
 * Glide -> as glide, downloads a resource that can then be passed into the [getBitmapFromDrawable] function
 */

class RoundedImageView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    // Attribute Defaults
    private val defaultBorderColor = ContextCompat.getColor(context!!, R.color.color_secondary)
    private val defaultBorderRadius = 4
    private val defaultImageColor = ContextCompat.getColor(context!!, android.R.color.white)
    private val defaultBackgroundColor = ContextCompat.getColor(context!!, R.color.color_secondary)
    private val defaultHighlightColor = 0x32000000

    private var bitmapShader: Shader? = null
    private var shaderMatrix: Matrix? = null

    private var bitmapRect: RectF? = null
    private var strokeRect: RectF? = null

    private var bitmap: Bitmap? = null

    private var bitmapPaint: Paint? = null
    private var borderPaint: Paint? = null
    private var backgroundPaint: Paint? = null
    private var pressedPaint: Paint? = null

    private var initialized = false
    private var viewPressed = false
    private var highlightEnabled = false

    // Customizable Attributes
    private var borderColor: Int = Color.TRANSPARENT
    private var borderWidth: Int = 2
    private var borderRadius: Int = 8 // in dp
    private var backgColor: Int = Color.TRANSPARENT
    private var highlightIsEnabled: Boolean = true
    private var highlightColor: Int = Color.TRANSPARENT

    init {

        if (attrs != null) {
            val attributeArray = context?.obtainStyledAttributes(attrs, R.styleable.RoundedImageView, 0, 0)
            borderColor = attributeArray!!.getColor(R.styleable.RoundedImageView_riv_borderColor, defaultBorderColor)
            borderWidth = attributeArray.getInteger(R.styleable.RoundedImageView_riv_borderWidth, 0)
            borderRadius = attributeArray.getInteger(R.styleable.RoundedImageView_riv_borderRadius, defaultBorderRadius)
            backgColor = attributeArray.getColor(R.styleable.RoundedImageView_riv_backgroundColor, defaultBackgroundColor)

            highlightIsEnabled = attributeArray.getBoolean(R.styleable.RoundedImageView_riv_highlightEnabled, true)
            highlightColor = attributeArray.getColor(R.styleable.RoundedImageView_riv_highlightColor, defaultHighlightColor)
            attributeArray.recycle()
        }

        shaderMatrix = Matrix()

        strokeRect = RectF()
        bitmapRect = RectF()

        bitmapPaint = Paint(ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = backgColor
        }

        borderPaint = Paint(ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = borderColor
            strokeWidth = borderWidth.toFloat()
        }

        backgroundPaint = Paint(ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = backgColor
        }

        pressedPaint = Paint(ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = highlightColor
        }

        highlightEnabled = highlightIsEnabled
        initialized = true

        setupBitmap()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val halfStrokeWidth = borderPaint!!.strokeWidth / 2f
        updateDrawBounds(bitmapRect)
        strokeRect!!.set(bitmapRect)
        strokeRect!!.inset(halfStrokeWidth, halfStrokeWidth)
        updateBitmapSize()
    }

    override fun onDraw(canvas: Canvas) {
        drawBackground(canvas)
        drawBitmap(canvas)
        drawStroke(canvas)
        drawHighlight(canvas)
    }

    private fun drawBackground(canvas: Canvas) {
        val convertedBorderRadius = dpToPx(borderRadius)

        canvas.drawRoundRect(
            RectF(0.0f, 0.0f, width.toFloat(), height.toFloat()),
            convertedBorderRadius,
            convertedBorderRadius,
            backgroundPaint!!
        )
    }

    private fun drawHighlight(canvas: Canvas) {
        if (highlightEnabled && viewPressed) {
            canvas.drawOval(bitmapRect!!, pressedPaint!!)
        }
    }

    private fun drawStroke(canvas: Canvas) {
        val convertedBorderRadius = dpToPx(borderRadius)
        val density = context.resources.displayMetrics.density

        canvas.drawRoundRect(
            RectF(borderWidth.toFloat() / density, borderWidth.toFloat() / density, width.toFloat() - borderWidth / density, height.toFloat() - borderWidth / density),
            convertedBorderRadius,
            convertedBorderRadius,
            borderPaint!!
        )
    }

    private fun drawBitmap(canvas: Canvas) {
        val convertedBorderRadius = dpToPx(borderRadius)

        canvas.drawRoundRect(
            RectF(0F, 0F, width.toFloat(), height.toFloat()),
            convertedBorderRadius,
            convertedBorderRadius,
            bitmapPaint!!
        )
    }

    private fun updateDrawBounds(bounds: RectF?) {
        val contentWidth = width - paddingLeft - paddingRight.toFloat()
        val contentHeight = height - paddingTop - paddingBottom.toFloat()

        var leftPadding = paddingLeft.toFloat()
        var topPadding = paddingTop.toFloat()

        if (contentWidth > contentHeight) {
            leftPadding += (contentWidth - contentHeight) / 2f
        } else {
            topPadding += (contentHeight - contentWidth) / 2f
        }
        val diameter = Math.min(contentWidth, contentHeight)
        bounds!![leftPadding, topPadding, leftPadding + diameter] = topPadding + diameter
    }

    private fun setupBitmap() {
        if (!initialized) {
            return
        }
        bitmap = getBitmapFromDrawable(drawable)
        if (bitmap == null) {
            return
        }
        bitmapShader = BitmapShader(bitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        bitmapPaint!!.shader = bitmapShader
        updateBitmapSize()
    }

    private fun updateBitmapSize() {
        if (bitmap == null) return
        val dx: Float
        val dy: Float
        val scale: Float
        // scale up/down with respect to this view size and maintain aspect ratio
        // translate bitmap position with dx/dy to the center of the image

        if (bitmap!!.width < bitmap!!.height) {
            // Scale based on width
            scale = bitmapRect!!.width() / bitmap!!.width.toFloat()
            dx = bitmapRect!!.left
            dy = bitmapRect!!.top - bitmap!!.height * scale / 2f + bitmapRect!!.width() / 2f
        } else {
            // Scale based on height
            scale = bitmapRect!!.height() / bitmap!!.height.toFloat()
            dx = bitmapRect!!.left - bitmap!!.width * scale / 2f + bitmapRect!!.width() / 2f
            dy = bitmapRect!!.top
        }
        shaderMatrix!!.setScale(scale, scale)
        shaderMatrix!!.postTranslate(dx, dy)
        bitmapShader!!.setLocalMatrix(shaderMatrix)
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private fun isInCircle(x: Float, y: Float): Boolean { // find the distance between center of the view and x,y point
        val distance = sqrt(
            (bitmapRect!!.centerX() - x.toDouble()).pow(2.0) + (bitmapRect!!.centerY() - y.toDouble()).pow(
                2.0
            )
        )
        return distance <= bitmapRect!!.width() / 2
    }

    private fun dpToPx(value: Int): Float {
        return value * context.resources.displayMetrics.density
    }

    /***********************************************
     * Uses [onTouchEvent] [MotionEvent.ACTION_DOWN], [MotionEvent.ACTION_UP] & [MotionEvent.ACTION_CANCEL]
     * to determine when the view is tapped.
     ***********************************************/

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var processed = false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isInCircle(event.x, event.y)) {
                    return false
                }
                processed = true
                viewPressed = true
                invalidate()
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                processed = true
                viewPressed = false
                invalidate()
                if (!isInCircle(event.x, event.y)) {
                    return false
                }
            }
        }
        return super.onTouchEvent(event) || processed
    }

    /***********************************************
     * Overrides [AppCompatImageView] setup methods
     ***********************************************/

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        setupBitmap()
    }

    override fun setImageDrawable(@Nullable drawable: Drawable?) {
        super.setImageDrawable(drawable)
        setupBitmap()
    }

    override fun setImageBitmap(@Nullable bm: Bitmap?) {
        super.setImageBitmap(bm)
        setupBitmap()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        setupBitmap()
    }

    /***********************************************
     * Methods for getting/setting programatically
     ***********************************************/

    @ColorInt
    fun getBorderColor(): Int {
        return borderPaint!!.color
    }

    fun setBorderColor(@ColorInt color: Int) {
        borderPaint!!.color = color
        invalidate()
    }

    @Dimension
    fun getBorderWidth(): Float {
        return borderPaint!!.strokeWidth
    }

    fun setBorderWidth(@Dimension width: Float) {
        borderPaint!!.strokeWidth = width
        invalidate()
    }

    fun getBorderRadius(): Float {
        return borderRadius.toFloat()
    }

    fun setBorderRadius(radius: Int) {
        borderRadius = radius
        invalidate()
    }

    fun getBgColor() : Int {
        return backgroundPaint!!.color
    }

    fun setBgColor(@ColorInt color: Int)  {
        backgroundPaint!!.color = color
        invalidate()
    }

    fun isHighlightEnable(): Boolean {
        return highlightEnabled
    }

    fun setHighlightEnable(enable: Boolean) {
        highlightEnabled = enable
        invalidate()
    }

    @ColorInt
    fun getHighlightColor(): Int {
        return pressedPaint!!.color
    }

    fun setHighlightColor(@ColorInt color: Int) {
        pressedPaint!!.color = color
        invalidate()
    }
}


