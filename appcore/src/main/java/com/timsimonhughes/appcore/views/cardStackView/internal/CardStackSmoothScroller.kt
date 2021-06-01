package com.timsimonhughes.appcore.views.cardStackView.internal

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.timsimonhughes.appcore.views.cardStackView.CardStackLayoutManager
import com.timsimonhughes.appcore.views.cardStackView.CardStackListener
import com.timsimonhughes.appcore.views.cardStackView.RewindAnimationSetting
import com.timsimonhughes.appcore.views.cardStackView.config.Direction


open class CardStackSmoothScroller(
    private val type: ScrollType,
    private var manager: CardStackLayoutManager
) : SmoothScroller() {

    enum class ScrollType {
        AutomaticSwipe, AutomaticRewind, ManualSwipe, ManualCancel
    }

    override fun onSeekTargetStep(dx: Int, dy: Int, state: RecyclerView.State, action: Action) {
        if (type == ScrollType.AutomaticRewind) {
            val setting: RewindAnimationSetting =
                manager.getCardStackSetting().rewindAnimationSetting
            action.update(
                -getDx(setting),
                -getDy(setting),
                setting.getDuration(),
                setting.getInterpolator()
            )
        }
    }

    override fun onTargetFound(targetView: View, state: RecyclerView.State, action: Action) {
        val x = targetView.translationX.toInt()
        val y = targetView.translationY.toInt()
        val setting: AnimationSetting

        when (type) {
            ScrollType.AutomaticSwipe -> {
                setting = manager.getCardStackSetting().swipeAnimationSetting
                action.update(
                    -getDx(setting),
                    -getDy(setting),
                    setting.getDuration(),
                    setting.getInterpolator()
                )
            }
            ScrollType.AutomaticRewind -> {
                setting = manager.getCardStackSetting().rewindAnimationSetting
                action.update(
                    x,
                    y,
                    setting.getDuration(),
                    setting.getInterpolator()
                )
            }
            ScrollType.ManualSwipe -> {
                val dx = -x * 10
                val dy = -y * 10
                setting = manager.getCardStackSetting().swipeAnimationSetting
                action.update(
                    dx,
                    dy,
                    setting.getDuration(),
                    setting.getInterpolator()
                )
            }
            ScrollType.ManualCancel -> {
                setting = manager.getCardStackSetting().rewindAnimationSetting
                action.update(
                    x,
                    y,
                    setting.getDuration(),
                    setting.getInterpolator()
                )
            }
        }
    }

    override fun onStart() {
        val listener: CardStackListener? = manager.getCardStackListener()
        val state: CardStackState = manager.getCardStackState()

        when (type) {
            ScrollType.AutomaticSwipe -> {
                state.next(CardStackState.Status.AutomaticSwipeAnimating)
                manager.getTopView()?.let { listener?.onCardDisappeared(it, manager.getTopPosition()) }
            }
            ScrollType.AutomaticRewind -> state.next(CardStackState.Status.RewindAnimating)
            ScrollType.ManualSwipe -> {
                state.next(CardStackState.Status.ManualSwipeAnimating)
                manager.getTopView()?.let { listener?.onCardDisappeared(it, manager.getTopPosition()) }
            }
            ScrollType.ManualCancel -> state.next(CardStackState.Status.RewindAnimating)
        }
    }

    override fun onStop() {
        val listener: CardStackListener? = manager.getCardStackListener()
        when (type) {
            ScrollType.AutomaticSwipe -> {
            }
            ScrollType.AutomaticRewind -> {
                listener?.onCardRewound()
                manager.getTopView()?.let { listener?.onCardAppeared(it, manager.getTopPosition()) }
            }
            ScrollType.ManualSwipe -> {
            }
            ScrollType.ManualCancel -> listener?.onCardCancelled()
        }
    }

    private fun getDx(setting: AnimationSetting): Int {
        val state: CardStackState = manager.getCardStackState()

        var dx = 0
        when (setting.getDirection()) {
            Direction.Left -> dx =
                -state.width * 2
            Direction.Right -> dx =
                state.width * 2
            Direction.Top, Direction.Bottom -> dx =
                0
        }
        return dx
    }

    private fun getDy(setting: AnimationSetting): Int {
        val state: CardStackState = manager.getCardStackState()

        var dy = 0
        when (setting.getDirection()) {
            Direction.Left, Direction.Right -> dy =
                state.height / 4
            Direction.Top -> dy =
                -state.height * 2
            Direction.Bottom -> dy =
                state.height * 2
        }
        return dy
    }

    init {
        this.manager = manager
    }
}