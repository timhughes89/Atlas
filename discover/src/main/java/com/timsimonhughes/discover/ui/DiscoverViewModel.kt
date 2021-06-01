package com.timsimonhughes.discover.ui

import androidx.lifecycle.ViewModel
import com.timsimonhughes.discover.data.DiscoverRepository
import com.timsimonhughes.discover.model.Planet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
class DiscoverViewModel(private val discoverRepository: DiscoverRepository) : ViewModel() {

//    private val _missionData: MutableStateFlow<List<Planet>> = discoverRepository.getData(AtlasApp.context)
//    val missionData: StateFlow<List<Planet>> = _missionData

    private val _planetList: MutableStateFlow<List<Planet>> = discoverRepository.getItems()
    val planetList: StateFlow<List<Planet>> = _planetList

//    fun getMissionData() {
//        viewModelScope.launch {
//            discoverRepository.getData(AtlasApp.context)
//        }
//    }

    fun getPlanetData(itemId: Int) : Planet? {
        return discoverRepository.getItem(itemId)
    }

    fun getPlanetsArray() : ArrayList<Planet> {
        return discoverRepository.examples
    }
}