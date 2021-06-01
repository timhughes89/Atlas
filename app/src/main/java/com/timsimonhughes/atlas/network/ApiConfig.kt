package com.timsimonhughes.atlas.network

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class ApiConfig {

//    https://api.nasa.gov/planetary/apod?=start_dat

    companion object {
        const val NASA_BASE_URL = "https://api.nasa.gov/"
        const val API_KEY = "pl521eBJWqAwQ8rIOpleax8WwD9e2aXaxhCpjw0U"

        const val maxStale = 60 * 60 * 24 * 28 // Tolerate 4 weeks stale
        const val cacheSize = 10 * 1024 * 1024
    }

    fun addLoggingInterceptor(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(loggingInterceptor)

        return httpClientBuilder.build()
    }

    fun addCache(context: Context?): OkHttpClient {
        val cache = context?.cacheDir?.let { Cache(it, cacheSize.toLong()) }

        val client = OkHttpClient().newBuilder().cache(cache).addInterceptor(object : Interceptor {

            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                if (!isNetworkAvailable(context)) {
                    request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=${maxStale}")
                        .build()
                }
                return chain.proceed(request)
            }
        }).build()

        return client
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}