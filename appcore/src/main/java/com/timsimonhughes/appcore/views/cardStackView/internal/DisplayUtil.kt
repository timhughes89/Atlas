package com.timsimonhughes.appcore.views.cardStackView.internal

import android.content.Context

object DisplayUtil {
    fun dpToPx(context: Context, dp: Float): Int {
        val density: Float = context.resources.displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }
}