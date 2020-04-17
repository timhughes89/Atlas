package com.timsimonhughes.atlas.network

import com.timsimonhughes.atlas.model.POTD
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaPotdService {

    @GET("planetary/apod")
    fun getPhotoOfDay(@Query("api_key") apiKey: String): Call<POTD>

    @GET("planetary/apod")
    fun getPOTDByDate(
            @Query("date") date: String,
            @Query("api_key") apiKey: String): Call<POTD>

    @GET("planetary/apod")
    fun getPOTDByDateRange(
            @Query("start_date") startDate: String,
            @Query("end_date") endDate: String,
            @Query("api_key") apiKey: String): Call<List<POTD>>

}
