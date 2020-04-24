package com.timsimonhughes.atlas.ui.discover.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timsimonhughes.atlas.AtlasApp
import com.timsimonhughes.atlas.model.Planet
import com.timsimonhughes.atlas.ui.discover.repository.DiscoverRepository

class DiscoverViewModel : ViewModel() {

    private val repository = DiscoverRepository()

    private val discover: MutableLiveData<List<Planet>> = repository.getMissionData(AtlasApp.context)
    val missionData: LiveData<List<Planet>> = discover

}