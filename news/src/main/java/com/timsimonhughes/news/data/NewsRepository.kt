package com.timsimonhughes.news.data

import androidx.lifecycle.MutableLiveData
import com.timsimonhughes.news.model.News
import com.timsimonhughes.news.model.NewsType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
class NewsRepository {

    val news = MutableLiveData<List<News>>()

    private val itemList = listOf(
        News(0, "https://images.unsplash.com/photo-1494022299300-899b96e49893?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2850&q=80.jpg", "", "Nasa launches for mars something here to push out all the item sizes", "On July 3rd, planet Earth reached aphelion, the farthest point in its elliptical orbit around the Sun. Each year, this day of the most distant Sun happens to occur during winter in the southern hemisphere. That's where this aphelion sunrise from 2015 was captured in a time series composite against the skyline of Brisbane, Australia. Of course, seasons for our fair planet are not determined by distance to the Sun, but by the tilt of Earth's rotational axis with respect to the ecliptic, the plane of its orbit. Fondly known as the obliquity of the ecliptic, the angle of the tilt is about 23.4 degrees from perpendicular to the orbital plane. So the most distant sunrise occurs during northern summer, when the planet's north pole is tilted toward the Sun and the north enjoys longer, warmer days.", NewsType.HUMANS),
        News(1, "https://images.unsplash.com/photo-1507499739999-097706ad8914?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2158&q=80.jpg", "", "Title 1", "", NewsType.HUMANS),
        News(2, "https://images.unsplash.com/photo-1451187580459-43490279c0fa?ixlib=rb-1.2.1&auto=format&fit=crop&w=2852&q=80.jpg", "", "Title 2", "", NewsType.SOLAR),
        News(3, "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?ixlib=rb-1.2.1&auto=format&fit=crop&w=1504&q=80.jpg", "", "Title 3",  "", NewsType.MOON),
        News(4, "https://images.unsplash.com/photo-1469980098053-382eb10ba017?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1500&q=80.jpg", "", "Title 4",  "", NewsType.HISTORY),
        News(5, "https://images.unsplash.com/photo-1484589065579-248aad0d8b13?ixlib=rb-1.2.1&auto=format&fit=crop&w=792&q=80.jpg", "", "Title 5",  "", NewsType.SPACE_TECH),
        News(6, "https://images.unsplash.com/photo-1454789415558-bdda08f4eabb?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1000&q=80.jpg", "", "Title 6",  "", NewsType.EARTH),
        News(7, "https://images.unsplash.com/photo-1446941611757-91d2c3bd3d45?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=866&q=80.jpg", "", "Title 7", "", NewsType.HUMANS),
        News(8, "https://images.unsplash.com/photo-1469980098053-382eb10ba017?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1500&q=80.jpg", "", "Title 8" , "", NewsType.SPACE_TECH),
        News(9, "https://images.unsplash.com/photo-1446776877081-d282a0f896e2?ixlib=rb-1.2.1&auto=format&fit=crop&w=1504&q=80", "", "Title 9", "", NewsType.SOLAR),
        News(10, "https://images.unsplash.com/photo-1462331940025-496dfbfc7564?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1127&q=80.jpg", "", "Title 10", "", NewsType.EARTH),
        News(11, "https://images.unsplash.com/photo-1502481851512-e9e2529bfbf9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1500&q=80", "", "Title 11", "", NewsType.HUMANS)
    )

    fun getNews() : MutableStateFlow<List<News>> {
        val newsList = MutableStateFlow<List<News>>(emptyList())
        newsList.value = itemList
        return newsList
    }

    fun findItem(itemId: Int) : News? {
        return itemList.find {
            itemId == it.id
        }
    }

    fun filterItems(checkedId: NewsType) {
        if (checkedId == NewsType.ALL) {
            news.value = itemList
        } else {
            news.value = itemList.filter { index -> index.tag == checkedId }
        }
    }
}
