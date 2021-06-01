package com.timsimonhughes.appcore.views.cardStackView.internal

import com.timsimonhughes.appcore.views.cardStackView.config.Direction

/**
 * Interface for passing
 * **/
interface AnimationSetting {
    fun getDirection(): Direction?
    fun getDuration(): Int
    fun getInterpolator(): android.view.animation.Interpolator?
}