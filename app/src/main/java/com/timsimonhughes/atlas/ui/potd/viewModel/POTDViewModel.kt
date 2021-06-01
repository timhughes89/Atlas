package com.timsimonhughes.atlas.ui.potd.viewModel

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.timsimonhughes.atlas.common.enums.NetworkState

import com.timsimonhughes.atlas.model.potd.POTD
import com.timsimonhughes.atlas.model.potd.POTDListing
import com.timsimonhughes.atlas.ui.potd.repository.POTDRepository

class POTDViewModel(private val potdRepository: POTDRepository) : ViewModel() {

    private lateinit var listing: POTDListing<POTD>
    private lateinit var feedData: LiveData<PagedList<POTD>>
    private lateinit var networkState: LiveData<NetworkState>
    private lateinit var refreshState: LiveData<NetworkState>
    private val pageSize = 20

    fun getItems(startDate: String, endDate: String) : LiveData<List<POTD>> {
        return potdRepository.getItems(startDate, endDate)
    }

    val config = PagedList.Config.Builder()
        .setPageSize(pageSize)
        .setEnablePlaceholders(true)
        .setPrefetchDistance(10)
        .setInitialLoadSizeHint(pageSize * 2)
        .build()

    fun getPOTDList(startDate: String, endDate: String) : LiveData<PagedList<POTD>> {
        listing = potdRepository.potdPosts(config, startDate, endDate)

        feedData = listing.pagedList
        networkState = listing.networkState
        refreshState = listing.refreshState

        return feedData
    }

    fun currentNetworkState(): LiveData<NetworkState> = networkState
    fun initialNetworkState(): LiveData<NetworkState> = refreshState
}