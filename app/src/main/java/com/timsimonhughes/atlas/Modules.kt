package com.timsimonhughes.atlas

import com.timsimonhughes.appcore.AppCoreModule
import com.timsimonhughes.atlas.network.ApiConfig
import com.timsimonhughes.atlas.network.ApiConfig.Companion.NASA_BASE_URL
import com.timsimonhughes.atlas.network.NasaService
import com.timsimonhughes.atlas.ui.potd.repository.POTDRepository
import com.timsimonhughes.atlas.ui.potd.viewModel.POTDViewModel
import com.timsimonhughes.discover.DiscoverModule
import com.timsimonhughes.news.NewsModule
import com.timsimonhughes.network.NasaModule
import com.timsimonhughes.spacex.SpaceXModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
object Modules {

    private val retryExecutor: ExecutorService = Executors.newFixedThreadPool(2)

    var modules = lazy {
        listOf(
            appModule.value,
            AppCoreModule.module.value,
            SpaceXModule.module.value,
            NewsModule.module.value,
            NasaModule.module.value,
            DiscoverModule.module.value
        )
    }

    private val appModule = lazy {
        module {

            // Retrofit instance
            factory {
                Retrofit.Builder()
                    .baseUrl(NASA_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(ApiConfig().addLoggingInterceptor())
                    .build()
                    .create(NasaService::class.java)
            }

//            single { NewsRepository() }
//            viewModel { NewsViewModel(get()) }

            single { POTDRepository(get(), retryExecutor) }
            viewModel { POTDViewModel(get()) }
        }
    }
}