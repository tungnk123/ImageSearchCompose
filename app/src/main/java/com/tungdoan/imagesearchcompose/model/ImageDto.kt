package com.tungdoan.imagesearchcompose.model

import com.tungdoan.imagesearchcompose.utils.Constants

data class ImageDto(
    val title: String,
    val imageUrl: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val thumbnailUrl: String,
    val thumbnailWidth: Int,
    val thumbnailHeight: Int,
    val source: String,
    val domain: String,
    val link: String,
    val googleUrl: String,
    val position: Int
)


fun ImageDto.toEntity(currentPage: Int): ImageEntity {
    return ImageEntity(
        id = this.position + (currentPage - 1) * Constants.PAGE_SIZE,
        imageUrl = this.imageUrl,
        sourceUrl = this.link
    )
}