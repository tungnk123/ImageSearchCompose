package com.tungdoan.imagesearchcompose.network

import com.tungdoan.imagesearchcompose.model.ImagesResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
interface ImagesApiService {
    @POST("/images")
    suspend fun getImages(
        @Query("q") query: String,
        @Query("num") num: Int = 10,
        @Query("page") page: Int,
    ): ImagesResponse
}