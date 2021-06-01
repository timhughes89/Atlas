package com.timsimonhughes.spacex.ui

import android.os.Bundle
import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView

/**
 * Persists scroll state for nested RecyclerViews.
 *
 * 1. Call [bind] when in [RecyclerView.Adapter.onBindViewHolder]
 * to have the RecyclerView scroll state monitored
 *
 * 2. Call [unbind] in [RecyclerView.Adapter.onViewRecycled]
 * to save the scroll position.
 *
 * 3. Call [restoreScrollState] in [RecyclerView.Adapter.onBindViewHolder]
 * after changing the adapter's contents to restore the scroll position
 */
class ScrollStateHolder(savedInstanceState: Bundle? = null) {

    /**
     * Provides a key that uniquely identifies a RecyclerView
     */
    interface ScrollStateKeyProvider {
        fun getScrollStateKey(): String?
    }


    /**
     * Persists the [RecyclerView.LayoutManager] states
     */
    private val scrollStates = hashMapOf<String, Parcelable>()

    /**
     * Keeps track of the keys that point to RecyclerViews
     * that have new scroll states that should be saved
     */
    private val scrolledKeys = mutableSetOf<String>()

    init {
        savedInstanceState?.let { restoreInstanceState(it) }
    }

    /**
     * To be called when the [savedInstanceState] is available.
     */
    fun restoreInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.getBundle(STATE_BUNDLE)?.let { bundle ->
            bundle.keySet().forEach { key ->
                bundle.getParcelable<Parcelable>(key)?.let {
                    scrollStates[key] = it
                }
            }
        }
    }

    /**
     * To be called when the Activity/Fragment is being destroyed and the state needs to be persisted
     * to savedInstanceState.
     */
    fun onSaveInstanceState(outState: Bundle) {
        val stateBundle = Bundle()
        scrollStates.entries.forEach {
            stateBundle.putParcelable(it.key, it.value)
        }
        outState.putBundle(STATE_BUNDLE, stateBundle)
    }

    /**
     * Adds a [RecyclerView.OnScrollListener] to [recyclerView] so that scrolls are monitored and stored.
     */
    fun bind(recyclerView: RecyclerView, scrollKeyProvider: ScrollStateKeyProvider) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    saveScrollState(recyclerView, scrollKeyProvider)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val key = scrollKeyProvider.getScrollStateKey()
                if (key != null && dx != 0) {
                    scrolledKeys.add(key)
                }
            }
        })
    }

    fun clearScrollState() {
        scrollStates.clear()
        scrolledKeys.clear()
    }

    fun unbind(recyclerView: RecyclerView, scrollKeyProvider: ScrollStateKeyProvider) {
        // remove any previous listeners we've set
        recyclerView.clearOnScrollListeners()
        saveScrollState(recyclerView, scrollKeyProvider)
    }

    /**
     * Saves this RecyclerView layout state for a given key
     */
    private fun saveScrollState(
            recyclerView: RecyclerView,
            scrollKeyProvider: ScrollStateKeyProvider
    ) {
        val key = scrollKeyProvider.getScrollStateKey() ?: return
        // Check if we scrolled the RecyclerView for this key
        if (scrolledKeys.contains(key)) {
            val layoutManager = recyclerView.layoutManager ?: return
            layoutManager.onSaveInstanceState()?.let { scrollStates[key] = it }
            scrolledKeys.remove(key)
        }
    }

    /**
     * Restores this RecyclerView layout state for a given key
     */
    fun restoreScrollState(
            recyclerView: RecyclerView,
            scrollKeyProvider: ScrollStateKeyProvider
    ) {
        val key = scrollKeyProvider.getScrollStateKey() ?: return
        val layoutManager = recyclerView.layoutManager ?: return
        val savedState = scrollStates[key]
        if (savedState != null) {
            layoutManager.onRestoreInstanceState(savedState)
        } else {
            // If we don't have any state for this RecyclerView,
            // make sure we reset the scroll position
            layoutManager.scrollToPosition(0)
        }
        // Mark this key as not scrolled since we just restored the state
        scrolledKeys.remove(key)
    }

    companion object {
        private const val STATE_BUNDLE = "scroll_state_bundle"
    }
}
