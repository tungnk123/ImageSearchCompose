package com.tungdoan.imagesearchcompose.data

import com.tungdoan.imagesearchcompose.model.ImagesResponse
import com.tungdoan.imagesearchcompose.network.ImagesApiService

class ImagesDataSource(
    private val imagesApiService: ImagesApiService
) {
    suspend fun getImageByQuery(query: String): ImagesResponse {
        return imagesApiService.getImages(query)
    }
}