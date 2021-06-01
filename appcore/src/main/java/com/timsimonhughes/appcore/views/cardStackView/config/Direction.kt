package com.timsimonhughes.appcore.views.cardStackView.config


enum class Direction {
    Left, Right, Top, Bottom;

    companion object {
        val HORIZONTAL: List<Direction> = listOf(
            Left,
            Right
        )
        val VERTICAL: List<Direction> = listOf(
            Top,
            Bottom
        )
        val FREEDOM: List<Array<Direction>> = listOf(values())
    }
}