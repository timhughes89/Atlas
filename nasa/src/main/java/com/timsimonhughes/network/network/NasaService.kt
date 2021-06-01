package com.timsimonhughes.network.network

import com.timsimonhughes.network.model.POTD
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaService {

//    Single request
//    https://api.nasa.gov/planetary/apod?api_key=pl521eBJWqAwQ8rIOpleax8WwD9e2aXaxhCpjw0U?start_date=2020-06-10?end_date=2020-01-10

//    Date range request
//    https://api.nasa.gov/planetary/apod?api_key=pl521eBJWqAwQ8rIOpleax8WwD9e2aXaxhCpjw0U&start_date=2020-06-02&end_date=2020-06-06

    //    https://api.nasa.gov/planetary/apod?=start_dat


    @GET("planetary/apod")
    fun getPhotoOfDay(@Query("api_key") apiKey: String): Call<POTD>

    @GET("planetary/apod")
    fun getPOTDByDate(
        @Query("date") date: String,
        @Query("api_key") apiKey: String
    ): Call<POTD>

    // Get initial
    @GET("planetary/apod")
    fun POTDInitial(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Call<List<POTD>>

    // Get after
    @GET("planetary/apod")
    fun POTDAfter(
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ) : Call<List<POTD>>

}
