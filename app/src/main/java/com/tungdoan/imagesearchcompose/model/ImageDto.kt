package com.tungdoan.imagesearchcompose.model

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


fun ImageDto.toEntity(): ImageEntity {
    return ImageEntity(
        id = this.position,
        imageUrl = this.imageUrl,
        sourceUrl = this.link
    )
}