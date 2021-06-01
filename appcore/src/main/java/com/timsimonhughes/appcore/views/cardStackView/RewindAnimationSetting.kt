package com.timsimonhughes.appcore.views.cardStackView

import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import com.timsimonhughes.appcore.views.cardStackView.config.Direction
import com.timsimonhughes.appcore.views.cardStackView.config.Duration
import com.timsimonhughes.appcore.views.cardStackView.internal.AnimationSetting


class RewindAnimationSetting(
    private var direction: Direction,
    private var duration: Int,
    private var interpolator: Interpolator) : AnimationSetting {

    class Builder {
        private var direction: Direction = Direction.Bottom
        private var duration: Int = Duration.Normal.value
        private var interpolator: Interpolator = DecelerateInterpolator()

        fun setDirection(direction: Direction): Builder {
            this.direction = direction
            return this
        }

        fun setDuration(duration: Int): Builder {
            this.duration = duration
            return this
        }

        fun setInterpolator(interpolator: Interpolator): Builder {
            this.interpolator = interpolator
            return this
        }

        fun build(): RewindAnimationSetting {
            return RewindAnimationSetting(
                direction,
                duration,
                interpolator
            )
        }
    }

    override fun getDirection(): Direction? {
        return direction
    }

    override fun getDuration(): Int {
        return duration
    }

    override fun getInterpolator(): Interpolator? {
        return interpolator
    }
}