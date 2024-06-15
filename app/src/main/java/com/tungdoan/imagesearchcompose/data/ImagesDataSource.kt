package com.tungdoan.imagesearchcompose.data

import android.util.Log
import com.tungdoan.imagesearchcompose.model.ImagesResponse
import com.tungdoan.imagesearchcompose.network.ImagesApiService
import okhttp3.RequestBody

class ImagesDataSource(
    private val imagesApiService: ImagesApiService
) {
    suspend fun getImageByQuery(
        query: String,
        page: Int,
    ): ImagesResponse {
        return imagesApiService.getImages(
            query = query,
            page = page
        )
    }
}