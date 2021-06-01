package com.timsimonhughes.discover

import com.timsimonhughes.discover.data.DiscoverRepository
import com.timsimonhughes.discover.ui.DiscoverViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
object DiscoverModule {
    val module = lazy {
        module {

            single { DiscoverRepository() }
            viewModel { DiscoverViewModel(get()) }
        }
    }
}