package com.tungdoan.imagesearchcompose

import com.google.gson.internal.GsonBuildConfig
import com.tungdoan.imagesearchcompose.data.ImagesDataSource
import com.tungdoan.imagesearchcompose.data.ImagesRepository
import com.tungdoan.imagesearchcompose.network.ImagesApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {

    private val apiKey = BuildConfig.API_KEY
    private val apiKeyInterceptor = Interceptor { chain ->
        val original: Request = chain.request()
        val request: Request = original.newBuilder()
            .header("X-API-KEY", apiKey)
            .header("Content-Type", "application/json")
            .build()
        chain.proceed(request)
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    private val baseUrl = "https://google.serper.dev"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val imagesApiService = retrofit.create(ImagesApiService::class.java)

    private val imagesDataSource: ImagesDataSource by lazy {
        ImagesDataSource(imagesApiService)
    }

    val imagesRepository: ImagesRepository by lazy {
        ImagesRepository(imagesDataSource)
    }


}