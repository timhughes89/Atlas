package com.timsimonhughes.spacex.network

import com.timsimonhughes.spacex.model.*
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interface for requesting data from the SpaceX api
 **/

interface SpaceXService {

    // Get all capsules
    @GET("v3/capsules")
    suspend fun getAllCapsules(): Response<List<Capsule>>

    // Get all cores
    @GET("v3/cores")
    suspend fun getAllCores(): Response<List<Core>>

    // Get company info
    @GET("v3/info")
    suspend fun getCompanyInfo(): Response<Company>

    // All Launches
    @GET("v3/launches")
    suspend fun getAllLaunches(): Response<List<Launch>>

    @GET("v3/launchpads")
    suspend fun getLaunchPads(): Response<List<LaunchPad>>
}