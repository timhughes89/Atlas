package com.timsimonhughes.atlas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    @LayoutRes
    abstract fun getLayoutRes(): Int
    abstract fun setUiState(state: UIState)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(getLayoutRes(), container, false)
    }

    enum class UIState {
        LOADING,
        LOADED,
        ERROR
    }
}