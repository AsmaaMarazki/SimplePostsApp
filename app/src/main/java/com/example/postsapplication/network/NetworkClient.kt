package com.example.postsapplication.network

import com.example.postsapplication.BuildConfig
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NetworkClient {

    private const val BASE_URL = "https://gorest.co.in/public/v2/"

    @Synchronized
    fun <S> createService(
        serviceClass: Class<S>,
        timeOut: Long = NetworkConfigurations.DEFAULT_TIME_OUT
    ): S {
        return getRetrofitBuilder()
            .client(initOkHttpClientBuilder(timeOut).build())
            .build()
            .create(serviceClass)
    }

    private fun initOkHttpClientBuilder(timeOut: Long = NetworkConfigurations.DEFAULT_TIME_OUT): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                if (!interceptors().contains(logging))
                    addInterceptor(logging)
            }
            readTimeout(timeOut, TimeUnit.SECONDS)
            connectTimeout(timeOut, TimeUnit.SECONDS)
            writeTimeout(NetworkConfigurations.WRITING_TIME_OUT, TimeUnit.SECONDS)
            connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
            retryOnConnectionFailure(true)
        }
    }

    private fun getRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

}