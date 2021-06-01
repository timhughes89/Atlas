package com.timsimonhughes.appcore.views.cardStackView

import android.view.View
import com.timsimonhughes.appcore.views.cardStackView.config.Direction

interface CardStackListener {
    fun onCardDragging(direction: Direction, ration: Float)
    fun onCardSwiped(direction: Direction)
    fun onCardRewound()
    fun onCardCancelled()
    fun onCardAppeared(view: View?, position: Int)
    fun onCardDisappeared(view: View, position: Int)
}