package com.timsimonhughes.appcore.views.cardStackView.internal

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.timsimonhughes.appcore.views.cardStackView.CardStackLayoutManager
import com.timsimonhughes.appcore.views.cardStackView.SwipeAnimationSetting
import com.timsimonhughes.appcore.views.cardStackView.config.Duration
import kotlin.math.abs

class CardStackSnapHelper : SnapHelper() {

    private var velocityX: Int = 0
    private var velocityY: Int = 0

    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray? {
        if (layoutManager is CardStackLayoutManager) {
            if (layoutManager.findViewByPosition(layoutManager.getTopPosition()) != null) {
                val x = targetView.translationX.toInt()
                val y = targetView.translationY.toInt()
                if (x != 0 || y != 0) {
                    val setting = layoutManager.getCardStackSetting()
                    val horizontal = abs(x) / targetView.width.toFloat()
                    val vertical = abs(y) / targetView.height.toFloat()
                    val duration: Duration = Duration.fromVelocity(if (velocityY < velocityX) velocityX else velocityY)

                    if (duration === Duration.Fast || setting.swipeThreshold < horizontal || setting.swipeThreshold < vertical) {
                        val state = layoutManager.getCardStackState()

                        if (setting.directions.contains(state.direction)) {
                            state.targetPosition = state.topPosition + 1

                            val swipeAnimationSetting: SwipeAnimationSetting = SwipeAnimationSetting.Builder()
                                .setDirection(setting.swipeAnimationSetting.getDirection())
                                .setDuration(duration.value)
                                .setInterpolator(setting.swipeAnimationSetting.getInterpolator())
                                .build()

                            layoutManager.setSwipeAnimationSetting(swipeAnimationSetting)

                            this.velocityX = 0
                            this.velocityY = 0

                            val scroller = CardStackSmoothScroller(CardStackSmoothScroller.ScrollType.ManualSwipe, layoutManager)
                            scroller.targetPosition = layoutManager.getTopPosition()
                            layoutManager.startSmoothScroll(scroller)

                        } else {
                            val scroller = CardStackSmoothScroller(CardStackSmoothScroller.ScrollType.ManualCancel, layoutManager)
                            scroller.targetPosition = layoutManager.getTopPosition()
                            layoutManager.startSmoothScroll(scroller)
                        }

                    } else {
                        val scroller = CardStackSmoothScroller(CardStackSmoothScroller.ScrollType.ManualCancel, layoutManager)
                        scroller.targetPosition = layoutManager.getTopPosition()
                        layoutManager.startSmoothScroll(scroller)
                    }
                }
            }
        }
        return IntArray(2)
    }

    override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager?, velocityX: Int, velocityY: Int): Int {
        this.velocityX = abs(velocityX)
        this.velocityY = abs(velocityY)

        if (layoutManager is CardStackLayoutManager) {
            val manager = layoutManager as CardStackLayoutManager
            return manager.getTopPosition()
        }

        return RecyclerView.NO_POSITION
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        if (layoutManager is CardStackLayoutManager) {
            val manager = layoutManager as CardStackLayoutManager
            val view = manager.findViewByPosition(manager.getTopPosition())
            view?.let {
                val x = view.translationX.toInt()
                val y = view.translationY.toInt()
                return if (x == 0 && y == 0) {
                    null
                } else view
            }
        }
        return null
    }
}