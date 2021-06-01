package com.timsimonhughes.spacex

import com.timsimonhughes.spacex.data.LaunchesRepository
import com.timsimonhughes.spacex.network.SpaceXService
import com.timsimonhughes.spacex.ui.LaunchesDetailViewModel
import com.timsimonhughes.spacex.ui.viewModel.LaunchesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SpaceXModule {
    val module = lazy {

        val SPACEX_BASE_URL = "https://api.spacexdata.com/v3/"

        module {

            // Retrofit instance
            factory {
                Retrofit.Builder()
                    .baseUrl(SPACEX_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(SpaceXService::class.java)
            }

            single { LaunchesRepository() }
            viewModel {
                LaunchesViewModel(
                    get()
                )
            }
            viewModel { LaunchesDetailViewModel(get()) }

        }
    }
}
