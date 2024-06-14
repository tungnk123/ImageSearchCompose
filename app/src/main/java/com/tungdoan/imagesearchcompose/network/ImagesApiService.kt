package com.tungdoan.imagesearchcompose.network

import com.tungdoan.imagesearchcompose.model.ImagesResponse
import retrofit2.http.GET
import retrofit2.http.Query
interface ImagesApiService {
    @GET("/images")
    suspend fun getImages(
        @Query("query") query: String
    ): ImagesResponse
}