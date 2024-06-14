package com.tungdoan.imagesearchcompose

import com.tungdoan.imagesearchcompose.data.ImagesDataSource
import com.tungdoan.imagesearchcompose.data.ImagesRepository
import com.tungdoan.imagesearchcompose.network.ImagesApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val baseUrl = "https://google.serper.dev"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imagesApiService = retrofit.create(ImagesApiService::class.java)

    private val imagesDataSource: ImagesDataSource by lazy {
        ImagesDataSource(imagesApiService)
    }

    private val imagesRepository: ImagesRepository by lazy {
        ImagesRepository(imagesDataSource)
    }



}