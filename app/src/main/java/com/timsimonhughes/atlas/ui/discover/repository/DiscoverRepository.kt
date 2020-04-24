package com.timsimonhughes.atlas.ui.discover.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.timsimonhughes.atlas.R
import com.timsimonhughes.atlas.model.Planet

class DiscoverRepository  {

    fun getMissionData(context: Context?) : MutableLiveData<List<Planet>> {

        val planetList = MutableLiveData<List<Planet>>()
        val jsonPlanetsString = context?.resources?.openRawResource(R.raw.planetary_bodies)?.bufferedReader().use { it!!.readText() }
        val moshi = Moshi.Builder().build()

        val type = Types.newParameterizedType(List::class.java, Planet::class.java)
        val adapter: JsonAdapter<List<Planet>> = moshi.adapter<List<Planet>>(type)
        val planets = adapter.fromJson(jsonPlanetsString)

        planetList.postValue(planets)

        return planetList
    }
}