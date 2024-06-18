package com.tungdoan.imagesearchcompose.ui.search_screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.tungdoan.imagesearchcompose.ImageSearchComposeApp
import com.tungdoan.imagesearchcompose.R
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ImageSearchScreen(
    modifier: Modifier = Modifier,
    imagesViewModel: ImagesViewModel,
    navController: NavHostController,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val context = LocalContext.current

    val uiState = imagesViewModel.uiState.collectAsState()
    var queryText by remember {
        mutableStateOf(uiState.value.query)
    }
    val lazyGridState = rememberLazyGridState()
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SearchTextField(
                queryText = queryText,
                onQueryChange = {
                    imagesViewModel.updateQuery(it)
                    queryText = it
                },
                onDeleteClick = {
                    queryText = ""
                },
                onSearchClick = {
                    imagesViewModel.getImagesByQuery(queryText, 1)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))

            GridImages(
                state = lazyGridState,
                imageUiState = uiState.value,
                loadNextPage = {
                    imagesViewModel.loadNextPage(queryText)
                },
                onImageClick = {
//                    Toast.makeText(context, "Image id: $it clicked", Toast.LENGTH_LONG).show()
                    navController.navigate("${ImageSearchComposeApp.DetailImageScreen.name}/$it")
                },
                animatedVisibilityScope = animatedVisibilityScope
            )
        }
    }
}

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    queryText: String,
    onQueryChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = queryText,
        onValueChange = onQueryChange,
        singleLine = true,
        placeholder = {
            Text(
                text = stringResource(R.string.title_search_text_field),
                style = MaterialTheme.typography.titleLarge
            )
        },
        shape = RoundedCornerShape(50),
        textStyle = MaterialTheme.typography.titleLarge,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon"
            )
        },
        trailingIcon = {
            if (queryText.isNotEmpty()) {
                IconButton(
                    onClick = onDeleteClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete icon"
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClick()
                keyboardController?.hide()
            }
        )
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.GridImages(
    modifier: Modifier = Modifier,
    state: LazyGridState,
    imageUiState: ImageUiState,
    loadNextPage: () -> Unit,
    onImageClick: (Int) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    if (imageUiState.imageList.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = state,
            modifier = Modifier.fillMaxSize(),
//            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(imageUiState.imageList) { item ->
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = "image/${item.id}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(durationMillis = 1000)
                            }
                        )
                        .aspectRatio(1.0f)
                        .clip(
                        shape = RoundedCornerShape(20.dp)
                    )
                        .clickable {
                                   onImageClick(item.id - 1)
                        },
                    placeholder = painterResource(R.drawable.img_place_holder),
                    error = painterResource(R.drawable.img_image_error),
                )
            }
            if (imageUiState.isLoading) {
                item(
                    span = {
                        GridItemSpan(2)
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            if (imageUiState.error != null) {
                item(
                    span = {
                        GridItemSpan(2)
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = imageUiState.error ?: "Unknown error",
                            color = Color.Red,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(16.dp).clickable {
                                loadNextPage()
                            }
                        )
                    }
                }
            }
            // Use LaunchedEffect to trigger loading of the next page when reaching the end of the list
            item {
                LaunchedEffect(state) {
                    snapshotFlow { state.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                        .filter { it == imageUiState.imageList.lastIndex }
                        .collect { loadNextPage() }
                }

            }
        }
    }
    else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.title_empty_image),
                style = MaterialTheme.typography.titleLarge
            )
            Image(
                painterResource(R.drawable.picture),
                contentDescription = null
            )
        }

    }
}