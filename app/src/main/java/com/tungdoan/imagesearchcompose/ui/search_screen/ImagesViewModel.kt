package com.tungdoan.imagesearchcompose.ui.search_screen

import androidx.lifecycle.ViewModel
import com.tungdoan.imagesearchcompose.data.ImagesRepository
import retrofit2.http.Query

class ImagesViewModel(
    private val imagesRepository: ImagesRepository
): ViewModel() {
    fun getImagesByQuery(query: String) {

    }
}