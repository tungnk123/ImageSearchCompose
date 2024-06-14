package com.tungdoan.imagesearchcompose.data

import com.tungdoan.imagesearchcompose.model.ImagesResponse

class ImagesRepository(
    private val imagesDataSource: ImagesDataSource
) {
    suspend fun getImagesByQuery(query: String): ImagesResponse {
        return imagesDataSource.getImageByQuery(query)
    }
}