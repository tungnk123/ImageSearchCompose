package com.tungdoan.imagesearchcompose.model

import android.speech.tts.TextToSpeech.Engine

data class ImagesResponse(
    val searchParameter: SearchParameter,
    val images: List<ImageDto>
)

data class SearchParameter(
    val q: String,
    val type: String,
    val engine: String,
    val num: Int
)
