package com.timsimonhughes.appcore.views.cardStackView

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.timsimonhughes.appcore.views.cardStackView.internal.CardStackDataObserver
import com.timsimonhughes.appcore.views.cardStackView.internal.CardStackSnapHelper

class CardStackRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val observer: CardStackDataObserver = CardStackDataObserver(this)

    init {
        CardStackSnapHelper().attachToRecyclerView(this)
        overScrollMode = OVER_SCROLL_NEVER
    }

    override fun setLayoutManager(layoutManager: LayoutManager?) {
        if (layoutManager is CardStackLayoutManager) {
            super.setLayoutManager(layoutManager)
        } else {
            throw IllegalArgumentException("CardStackView's layoutManager must be a CardStackLayoutManager")
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        if (layoutManager == null) {
            layoutManager = CardStackLayoutManager(context, null)
        }

        if (getAdapter() != null) {
            getAdapter()?.unregisterAdapterDataObserver(observer)
            getAdapter()?.onDetachedFromRecyclerView(this)
        }

        adapter?.registerAdapterDataObserver(observer)
        super.setAdapter(adapter)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val manager = layoutManager as CardStackLayoutManager?
            manager?.updateProportion(event.x, event.y)
        }
        return super.onInterceptTouchEvent(event)
    }

    fun swipe() {
        if (layoutManager is CardStackLayoutManager) {
            val manager = layoutManager as CardStackLayoutManager?
            smoothScrollToPosition(manager!!.getTopPosition() + 1)
        }
    }

    fun rewind() {
        val manager = layoutManager as CardStackLayoutManager?
        smoothScrollToPosition(manager!!.getTopPosition() - 1)
    }
}