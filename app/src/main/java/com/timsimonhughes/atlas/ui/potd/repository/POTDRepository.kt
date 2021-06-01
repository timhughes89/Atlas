package com.timsimonhughes.atlas.ui.potd.repository

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.timsimonhughes.atlas.data.NasaDataSourceFactory
import com.timsimonhughes.atlas.model.potd.POTD
import com.timsimonhughes.atlas.model.potd.POTDListing
import com.timsimonhughes.atlas.network.ApiConfig
import com.timsimonhughes.atlas.network.NasaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class POTDRepository(
    private val nasaService: NasaService,
    private val retryExecutor: Executor) {

    fun getItems( startDate: String, endDate: String): MutableLiveData<List<POTD>> {
        return getPOTD(startDate, endDate)
    }

    private fun getPOTD(startDate: String, endDate: String): MutableLiveData<List<POTD>> {
        val data = MutableLiveData<List<POTD>>()

        val request = nasaService.POTDInitial(startDate, endDate, ApiConfig.API_KEY)
        request.enqueue(object : Callback<List<POTD>> {
            override fun onFailure(call: Call<List<POTD>>, t: Throwable) {
            }

            override fun onResponse(call: Call<List<POTD>>, response: Response<List<POTD>>) {
                if (response.isSuccessful) {
                    data.postValue(response.body())
                }
            }
        })

        return data

    }

    @MainThread
    fun potdPosts(config: PagedList.Config?, startDate: String, endDate: String): POTDListing<POTD> {

        val nasaDataSourceFactory =
            NasaDataSourceFactory(
                nasaService,
                retryExecutor,
                startDate,
                endDate
            )
        val livePagedListBuilder = LivePagedListBuilder(nasaDataSourceFactory, config!!).build()

        return POTDListing(
            pagedList = livePagedListBuilder,
            networkState = nasaDataSourceFactory.sourceLiveData.switchMap {
                it.networkState
            },
            refreshState = nasaDataSourceFactory.sourceLiveData.switchMap {
                it.initialLoad
            },
            refresh = {
                nasaDataSourceFactory.sourceLiveData.value?.invalidate()
            },
            retry = {
                nasaDataSourceFactory.sourceLiveData.value?.retryAllFailed()
            }
        )
    }

}
