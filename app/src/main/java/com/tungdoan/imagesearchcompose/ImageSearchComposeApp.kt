package com.tungdoan.imagesearchcompose

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tungdoan.imagesearchcompose.ui.theme.ImageSearchComposeTheme
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tungdoan.imagesearchcompose.ui.detail_image_screen.ImageDetailScreen
import com.tungdoan.imagesearchcompose.ui.search_screen.ImageSearchScreen
import com.tungdoan.imagesearchcompose.ui.search_screen.ImagesViewModel

enum class ImageSearchComposeApp(@StringRes val title: Int) {
    SearchScreen(title = R.string.image_search_screen_route),
    DetailImageScreen(title = R.string.detail_image_screen_route)
}

@OptIn(ExperimentalSharedTransitionApi::class)
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
            SharedTransitionLayout {
                NavHost(
                    navController = navControler,
                    startDestination = ImageSearchComposeApp.SearchScreen.name,
                ) {
                    composable(
                        route = ImageSearchComposeApp.SearchScreen.name
                    ) {
                        ImageSearchScreen(
                            imagesViewModel = imagesViewModel,
                            navController = navControler,
                            animatedVisibilityScope = this
                        )
                    }

                    composable(
                        route = "${ImageSearchComposeApp.DetailImageScreen.name}/{index}"
                    ) {navBackStackEntry ->
                        val index = navBackStackEntry.arguments?.getString("index")?.toInt() ?: 0
                        ImageDetailScreen(
                            imagesViewModel = imagesViewModel,
                            startIndex = index,
                            navController = navControler,
                            animatedVisibilityScope = this
                        )
                    }


                }
            }
        }
    }
}