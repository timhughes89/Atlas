package com.timsimonhughes.spacex.ui

import androidx.lifecycle.ViewModel
import com.timsimonhughes.spacex.data.LaunchesRepository
import com.timsimonhughes.spacex.model.Capsule

class LaunchesDetailViewModel(private val launchesRepository: LaunchesRepository) : ViewModel() {

    fun getItemById(id: Long): Capsule? {
        return launchesRepository.findItemByid(id)
    }

}