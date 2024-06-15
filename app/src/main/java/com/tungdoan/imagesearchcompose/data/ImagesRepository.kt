package com.tungdoan.imagesearchcompose.data

import com.tungdoan.imagesearchcompose.model.ImageDto
import okhttp3.RequestBody

class ImagesRepository(
    private val imagesDataSource: ImagesDataSource
) {
    suspend fun getImagesByQuery(
        query: String,
        page: Int,
    ): List<ImageDto> {

        return imagesDataSource.getImageByQuery(
            query = query,
            page = page,
        ).images
    }
}