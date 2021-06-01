package com.timsimonhughes.news

import com.timsimonhughes.news.data.NewsRepository
import com.timsimonhughes.news.ui.viewModel.NewsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
object NewsModule {
    val module = lazy {

        module {
            single { NewsRepository() }
            viewModel { NewsViewModel(get()) }
        }
    }
}