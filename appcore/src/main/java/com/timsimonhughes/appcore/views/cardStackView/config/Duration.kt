package com.timsimonhughes.appcore.views.cardStackView.config

enum class Duration(val value: Int) {
    Fast(100),
    Normal(200),
    Slow(500);

    companion object {
        fun fromVelocity(velocity: Int): Duration {
            if (velocity < 1000) {
                return Slow
            } else if (velocity < 5000) {
                return Normal
            }
            return Fast
        }
    }
}