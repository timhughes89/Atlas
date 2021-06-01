package com.timsimonhughes.atlas.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.timsimonhughes.atlas.common.enums.NetworkState
import com.timsimonhughes.atlas.model.potd.POTD
import com.timsimonhughes.atlas.network.ApiConfig
import com.timsimonhughes.atlas.network.NasaService
import java.io.IOException
import java.util.concurrent.Executor

class NasaDataSource(
    private val nasaService: NasaService,
    private val retryExecutor: Executor,
    private val startDate: String,
    private val endDate: String) : ItemKeyedDataSource<String, POTD>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<POTD>) {
        val request = nasaService.POTDInitial(
            startDate = startDate,
            endDate = endDate,
            apiKey = ApiConfig.API_KEY)

        try {
            val response = request.execute()
            val data = response.body()
            val items = data?.map { it } ?: emptyList()
            retry = null
            callback.onResult(items)
        } catch (ioException: IOException) {
            retry = {
                loadInitial(params, callback)
            }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<POTD>) {
        val request = nasaService.POTDAfter(
            apiKey = ApiConfig.API_KEY,
            startDate = startDate,
            endDate = endDate
        )

        try {
            val response = request.execute()
            val data = response.body()
            val items = data?.map { it } ?: emptyList()
            retry = null
            callback.onResult(items)
        } catch (ioException: IOException) {
            retry = {
                loadAfter(params, callback)
            }
//            networkState.postValue(NetworkState.ERROR)
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<POTD>) {
        // Not using
    }

    override fun getKey(item: POTD) = item.title

}
