package com.tungdoan.imagesearchcompose.ui.search_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tungdoan.imagesearchcompose.AppContainer
import com.tungdoan.imagesearchcompose.ImageSearchComposeApplication
import com.tungdoan.imagesearchcompose.data.ImagesRepository
import com.tungdoan.imagesearchcompose.model.ImageEntity
import com.tungdoan.imagesearchcompose.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody


data class ImageUiState(
    val imageList: List<ImageEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
class ImagesViewModel(
    private val imagesRepository: ImagesRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ImageUiState())
    val uiState = _uiState.asStateFlow()

    fun getImagesByQuery(
        query: String,
        page: Int,
    ) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val imageDtos = imagesRepository.getImagesByQuery(query, page = page)
                Log.d("Api Response", imageDtos.toString())
                val imageEntities = imageDtos.map { it.toEntity() }
                _uiState.value = _uiState.value.copy(imageList = imageEntities, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ImageSearchComposeApplication)
                val imagesRepository = application.container.imagesRepository
                ImagesViewModel(imagesRepository)
            }
        }
    }
}