package com.timsimonhughes.spacex.ui.viewModel


import androidx.lifecycle.ViewModel
import com.timsimonhughes.spacex.data.LaunchesRepository
import com.timsimonhughes.spacex.model.Capsule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@ExperimentalCoroutinesApi
class LaunchesViewModel(private val launchesRepository: LaunchesRepository) : ViewModel() {

    private val _indicatorCountState = MutableStateFlow(0)
    val indicatorCountState: StateFlow<Int> = _indicatorCountState

    fun getItem() : String {
        return "This is the Launches Fragment"
    }

    fun getItemsArray() : ArrayList<Capsule> {
        return launchesRepository.capsulesArray
    }

    fun incrementIndicatorCount() {
        _indicatorCountState.value++
    }

    fun resetIndicatorCount() {
        _indicatorCountState.value = 0
    }
}