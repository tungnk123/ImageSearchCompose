package com.tungdoan.imagesearchcompose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tungdoan.imagesearchcompose.ui.theme.ImageSearchComposeTheme
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tungdoan.imagesearchcompose.ui.search_screen.ImageSearchScreen
import com.tungdoan.imagesearchcompose.ui.search_screen.ImagesViewModel

enum class ImageSearchComposeApp(@StringRes val title: Int) {
    SearchScreen(title = R.string.image_search_screen_route),
    DetailImageScreen(title = R.string.detail_image_screen_route)
}

@Composable
fun ImageSearchComposeApp(
    navControler: NavHostController = rememberNavController()
) {
    val imagesViewModel: ImagesViewModel = viewModel(factory = ImagesViewModel.factory)
    ImageSearchComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold {paddingValues ->
                NavHost(
                    navController = navControler,
                    startDestination = ImageSearchComposeApp.SearchScreen.name,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    composable(
                        route = ImageSearchComposeApp.SearchScreen.name
                    ) {
                        ImageSearchScreen(
                            imagesViewModel = imagesViewModel
                        )
                    }


                }
            }
        }
    }
}