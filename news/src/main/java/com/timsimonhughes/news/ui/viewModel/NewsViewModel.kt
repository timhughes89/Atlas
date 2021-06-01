package com.timsimonhughes.news.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timsimonhughes.news.model.News
import com.timsimonhughes.news.model.NewsType
import com.timsimonhughes.news.data.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

//    private val _newsData: MutableStateFlow<List<News>> = newsRepository.getNews()
//    val newsData: StateFlow<List<News>> = _newsData

    private val _newsData: MutableLiveData<List<News>> = newsRepository.news
    val newsData: LiveData<List<News>> = _newsData

    fun getItem(itemId: Int) : News? {
        return newsRepository.findItem(itemId)
    }

    fun filterItems(type: NewsType) {
        newsRepository.filterItems(type)
    }

//    fun filterItems(checkedChipId: NewsType) : List<News> {
//        return newsRepository.filterItems(checkedChipId)
//    }
}