package com.timsimonhughes.appcore.views.stackLayoutManager

import android.view.View
import com.timsimonhughes.appcore.views.stackLayoutManager.StackLayoutManager.ScrollOrientation

abstract class StackAnimation(scrollOrientation: ScrollOrientation, visibleCount: Int) {

    protected val mScrollOrientation = scrollOrientation
    protected var mVisibleCount = visibleCount

    internal fun setVisibleCount(visibleCount: Int) {
        mVisibleCount = visibleCount
    }

    abstract fun doAnimation(firstMovePercent: Float, itemView: View, position: Int)
}