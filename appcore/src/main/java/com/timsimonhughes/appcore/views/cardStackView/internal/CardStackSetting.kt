package com.timsimonhughes.appcore.views.cardStackView.internal

import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import com.timsimonhughes.appcore.views.cardStackView.RewindAnimationSetting
import com.timsimonhughes.appcore.views.cardStackView.SwipeAnimationSetting
import com.timsimonhughes.appcore.views.cardStackView.config.Direction
import com.timsimonhughes.appcore.views.cardStackView.config.StackFrom
import com.timsimonhughes.appcore.views.cardStackView.config.SwipeableMethod

/**
 * Holds the settings for  the CardStack, if no values are provided,
 * it falls back to these default settings.
 * **/

class CardStackSetting {
    var stackFrom: StackFrom = StackFrom.None
    var visibleCount = 3
    var translationInterval = 8.0f
    var scaleInterval = 0.95f // 0.0f - 1.0f
    var swipeThreshold = 0.3f // 0.0f - 1.0f
    var maxDegree = 10.0f
    var directions: List<Direction> = Direction.HORIZONTAL
    var canScrollHorizontal = true
    var canScrollVertical = true
    var swipeableMethod: SwipeableMethod = SwipeableMethod.AutomaticAndManual
    var swipeAnimationSetting: SwipeAnimationSetting = SwipeAnimationSetting.Builder().build()
    var rewindAnimationSetting: RewindAnimationSetting = RewindAnimationSetting.Builder().build()
    var overlayInterpolator: Interpolator = LinearInterpolator()
}