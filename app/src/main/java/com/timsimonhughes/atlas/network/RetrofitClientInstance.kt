package com.timsimonhughes.atlas.network

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class RetrofitClientInstance {

    private var retrofitInstance: Retrofit? = null

    fun getRetrofitInstance(context: Context?, cacheSize: Int) : Retrofit {

        retrofitInstance = Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(addLoggingInterceptor())
                .client(addCache(context, cacheSize))
                .build()

        return retrofitInstance as Retrofit
    }

    private fun addCache(context: Context?, cacheSize: Int): OkHttpClient {
        val cache = context?.cacheDir?.let { Cache(it, cacheSize.toLong()) }

        val client = OkHttpClient().newBuilder().cache(cache).addInterceptor(object: Interceptor {

            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                if (!isNetworkAvailable(context)) {
                    request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=$maxStale").build()
                }
                return chain.proceed(request)
            }
        }).build()

        return client
    }

    private fun addLoggingInterceptor(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(loggingInterceptor)

        return httpClientBuilder.build()
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    companion object {
        const val maxStale = 60 * 60 * 24 * 28 // Tolerate 4 weeks stale
    }

}