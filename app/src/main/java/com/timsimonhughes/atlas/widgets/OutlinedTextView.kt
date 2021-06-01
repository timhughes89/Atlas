package com.timsimonhughes.atlas.widgets

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.timsimonhughes.atlas.R

/**
 * A class that extends [AppCompatTextView]
 * Paints a linear gradient over a textview a can be configured to create a filled or outlined style
 * android:textColor must be set in xml for the outlined effect to work.
 */

class OutlinedTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var defaultStrokeWidth = 0.0f
    private val defaultGradientStartColor = ContextCompat.getColor(context,
        R.color.color_primary
    )
    private val defaultGradientEndColor = ContextCompat.getColor(context,
        R.color.color_secondary
    )

    // Styled variables XML
    private var providedStrokeColor = 0
    private var providedGradientStartColor = 0
    private var providedGradientEndColor = 0
    private var providedStrokeWidth = 0.0f

    private var convertedStrokeWidth: Int? = null

    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.OutlinedTextView
            )
            providedStrokeColor = typedArray.getColor(R.styleable.OutlinedTextView_otv_strokeColor, currentTextColor)
            providedStrokeWidth = typedArray.getFloat(R.styleable.OutlinedTextView_otv_strokeWidth, defaultStrokeWidth)
            providedGradientStartColor = typedArray.getColor(R.styleable.OutlinedTextView_otv_gradientColorStart, defaultGradientStartColor)
            providedGradientEndColor = typedArray.getColor(R.styleable.OutlinedTextView_otv_gradientColorEnd, defaultGradientEndColor)
            typedArray.recycle()
        }
        // Convert dimensions Dp value to px value to ensure scaled size based on screen density
        convertedStrokeWidth = dpToPx(context, providedStrokeWidth)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        // Re-setting the gradient if layout is changed
        if (changed) {
            updateShader()
        }
    }

    private fun updateShader() {
        paint.shader = LinearGradient(
                0.0f,
                0.0f,
                this.width.toFloat(),
                this.height.toFloat(),
                providedGradientStartColor,
                providedGradientEndColor,
                Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas?) {
        if (convertedStrokeWidth!! > 0) {
            val strokePaint = paint
            strokePaint.style = Paint.Style.FILL

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.typeface = resources.getFont(R.font.titillium_bold)
            }

            super.onDraw(canvas)

            val currentTextColor = currentTextColor
            strokePaint.style = Paint.Style.STROKE
            strokePaint.strokeWidth = providedStrokeWidth
            setTextColor(providedStrokeColor)

            super.onDraw(canvas)
            setTextColor(currentTextColor)
        }
    }

    private fun dpToPx(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}