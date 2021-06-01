package com.timsimonhughes.appcore.views.cardStackView

import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator
import com.timsimonhughes.appcore.views.cardStackView.config.Direction
import com.timsimonhughes.appcore.views.cardStackView.config.Duration
import com.timsimonhughes.appcore.views.cardStackView.internal.AnimationSetting

/**
 * Holds the SwipeAnimationSetting for [CardStackRecyclerView]
 * **/
class SwipeAnimationSetting private constructor(
    private val direction: Direction,
    private val duration: Int,
    private  val interpolator: Interpolator
) : AnimationSetting {

    override fun getDirection(): Direction? {
        return direction
    }

    override fun getDuration(): Int {
        return duration
    }

    override fun getInterpolator(): Interpolator? {
        return interpolator
    }

    class Builder {
        private var direction: Direction = Direction.Right
        private var duration: Int = Duration.Normal.value
        private var interpolator: Interpolator = AccelerateInterpolator()

        fun setDirection(direction: Direction?): Builder {
            if (direction != null) {
                this.direction = direction
            }
            return this
        }

        fun setDuration(duration: Int): Builder {
            this.duration = duration
            return this
        }

        fun setInterpolator(interpolator: Interpolator?): Builder {
            if (interpolator != null) {
                this.interpolator = interpolator
            }
            return this
        }

        fun build(): SwipeAnimationSetting {
            return SwipeAnimationSetting(
                direction,
                duration,
                interpolator
            )
        }
    }
}