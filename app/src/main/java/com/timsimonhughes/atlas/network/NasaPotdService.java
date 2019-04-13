package com.timsimonhughes.atlas.network;

import com.timsimonhughes.atlas.model.POTD;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NasaPotdService {

    @GET("planetary/apod")
    Call<POTD> getPhotoOfDay(@Query("api_key") String apiKey);


    @GET("planetary/apod")
    Call<POTD> getPOTDByDate(@Query("date") String date, @Query("api_key") String apiKey);

    @GET("planetary/apod")
    Call<List<POTD>> getPOTDByDateRange(@Query("start_date") String startDate, @Query("end_date") String endDate, @Query("api_key") String apiKey);

//    @GET("api/v1/rover/curiosity../mars-photos")
//    Call<List>


    ///////// PHOTO OF THE DAY EXAMPLE ENDPOINTS /////////
    // https://api.nasa.gov/planetary/apod?date=2019-02-01&api_key=pl521eBJWqAwQ8rIOpleax8WwD9e2aXaxhCpjw0U - SPECIFIC DATE QUERY
    // https://api.nasa.gov/planetary/apod?start_date=2018-01-01&end_date=2018-01-03&api_key=pl521eBJWqAwQ8rIOpleax8WwD9e2aXaxhCpjw0U - DATE RANGE QUERY


}
