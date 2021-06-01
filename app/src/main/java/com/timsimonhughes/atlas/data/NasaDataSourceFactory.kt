package com.timsimonhughes.atlas.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.timsimonhughes.atlas.data.NasaDataSource
import com.timsimonhughes.atlas.model.potd.POTD
import com.timsimonhughes.atlas.network.NasaService
import java.util.concurrent.Executor

class NasaDataSourceFactory(
    private val nasaService: NasaService,
    private val retryExecutor: Executor,
    private val startDate: String,
    private val endDate: String) : DataSource.Factory<String, POTD>() {

    val sourceLiveData = MutableLiveData<NasaDataSource>()

    override fun create(): DataSource<String, POTD> {
        val source = NasaDataSource(
            nasaService,
            retryExecutor,
            startDate,
            endDate
        )
        sourceLiveData.postValue(source)
        return source
    }
}
