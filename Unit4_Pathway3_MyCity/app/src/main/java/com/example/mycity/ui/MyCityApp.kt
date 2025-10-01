package com.example.mycity.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mycity.R
import com.example.mycity.model.Category
import com.example.mycity.model.Place
import com.example.mycity.utils.MyCityContentType
import com.example.mycity.utils.MyCityNavigationType
import com.example.mycity.viewmodel.CityViewModel



object MyCityRoute {
    const val CATEGORIES = "categories"
    const val PLACES = "places/{categoryId}"
    const val PLACE_DETAIL = "place/{placeId}"
}

@Composable
fun MyCityApp(
    windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact
) {
    val viewModel: CityViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()

    val navigationType: MyCityNavigationType
    val contentType: MyCityContentType

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = MyCityNavigationType.BOTTOM_NAVIGATION
            contentType = MyCityContentType.ListOnly
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = MyCityNavigationType.NAVIGATION_RAIL
            contentType = MyCityContentType.ListOnly
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = MyCityNavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = MyCityContentType.ListAndDetail
        }
        else -> {
            navigationType = MyCityNavigationType.BOTTOM_NAVIGATION
            contentType = MyCityContentType.ListOnly
        }
    }


    Scaffold(
        topBar = {
            MyCityAppBar(
                navController = navController,
                windowSize = windowSize,
                currentCategory = uiState.currentCategory
            )
        }
    ) { innerPadding ->
        if (contentType == MyCityContentType.ListAndDetail) {
            MyCityTwoPaneContent(
                viewModel = viewModel,
                uiState = uiState,
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            MyCityNavHost(
                navController = navController,
                viewModel = viewModel,
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCityAppBar(
    navController: NavHostController,
    windowSize: WindowWidthSizeClass,
    currentCategory: Category?,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val canNavigateBack = navController.previousBackStackEntry != null

    val title = when {
        currentRoute?.startsWith("places/") == true ->
            currentCategory?.let { stringResource(it.titleResourceId) } ?: stringResource(R.string.app_name)
        currentRoute?.startsWith("place/") == true ->
            stringResource(R.string.detail_fragment_label)
        else -> stringResource(R.string.app_name)
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            if (canNavigateBack && windowSize != WindowWidthSizeClass.Expanded) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
    )
}

@Composable
fun MyCityNavHost(
    navController: NavHostController,
    viewModel: CityViewModel,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = MyCityRoute.CATEGORIES,
        modifier = modifier
    ) {
        composable(MyCityRoute.CATEGORIES) {
            CategoriesList(
                categories = uiState.categoriesList,
                onClick = { category ->
                    viewModel.updateCurrentCategory(category)
                    navController.navigate("places/${category.id}")
                },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )
        }

        composable(MyCityRoute.PLACES) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")?.toIntOrNull() ?: 0
            viewModel.updateCurrentCategoryById(categoryId)

            PlacesList(
                places = uiState.placesForCategory,
                onClick = { place ->
                    viewModel.updateCurrentPlace(place)
                    navController.navigate("place/${place.id}")
                },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )
        }

        composable(MyCityRoute.PLACE_DETAIL) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId")?.toIntOrNull() ?: 0
            viewModel.updateCurrentPlaceById(placeId)

            uiState.currentPlace?.let { place ->
                PlaceDetail(
                    place = place,
                    onBackPressed = { navController.navigateUp() },
                    contentPadding = contentPadding
                )
            }
        }
    }
}

@Composable
fun MyCityTwoPaneContent(
    viewModel: CityViewModel,
    uiState: com.example.mycity.viewmodel.MyCityUiState,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.padding(contentPadding)) {
        CategoriesList(
            categories = uiState.categoriesList,
            selectedCategory = uiState.currentCategory,
            onClick = { category ->
                viewModel.updateCurrentCategory(category)
            },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        )

        if (uiState.currentCategory != null) {
            PlacesList(
                places = uiState.placesForCategory,
                selectedPlace = uiState.currentPlace,
                onClick = { place ->
                    viewModel.updateCurrentPlace(place)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = dimensionResource(R.dimen.padding_small))
            )
        }

        if (uiState.currentPlace != null) {
            PlaceDetail(
                place = uiState.currentPlace,
                onBackPressed = { viewModel.clearCurrentPlace() },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.weight(2f)
            )
        }
    }
}

@Composable
fun CategoriesList(
    categories: List<Category>,
    selectedCategory: Category? = null,
    onClick: (Category) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier.padding(top = dimensionResource(R.dimen.padding_medium))
    ) {
        items(categories, key = { it.id }) { category ->
            CategoryListItem(
                category = category,
                isSelected = category.id == selectedCategory?.id,
                onClick = { onClick(category) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListItem(
    category: Category,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.card_image_height))
        ) {
            Image(
                painter = painterResource(category.imageResourceId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.card_image_height))
            )
            Column(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_medium)
                    )
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(category.titleResourceId),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
                )
                Text(
                    text = stringResource(category.subtitleResourceId),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesList(
    places: List<Place>,
    selectedPlace: Place? = null,
    onClick: (Place) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier.padding(top = dimensionResource(R.dimen.padding_medium))
    ) {
        items(places, key = { it.id }) { place ->
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (place.id == selectedPlace?.id) 8.dp else 2.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = if (place.id == selectedPlace?.id)
                        MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
                onClick = { onClick(place) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.card_image_height))
                ) {
                    Image(
                        painter = painterResource(place.imageResourceId),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(dimensionResource(R.dimen.card_image_height))
                    )
                    Column(
                        modifier = Modifier
                            .padding(
                                vertical = dimensionResource(R.dimen.padding_small),
                                horizontal = dimensionResource(R.dimen.padding_medium)
                            )
                            .weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(place.titleResourceId),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = if (place.id == selectedPlace?.id) FontWeight.Bold else FontWeight.Medium,
                            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.card_text_vertical_space))
                        )
                        Text(
                            text = stringResource(place.shortDescriptionResourceId),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceDetail(
    place: Place,
    onBackPressed: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackPressed()
    }

    val scrollState = rememberScrollState()
    val layoutDirection = LocalLayoutDirection.current

    Box(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(top = contentPadding.calculateTopPadding())
    ) {
        Column(
            modifier = Modifier
                .padding(
                    bottom = contentPadding.calculateBottomPadding(),
                    start = contentPadding.calculateStartPadding(layoutDirection),
                    end = contentPadding.calculateEndPadding(layoutDirection)
                )
        ) {
            Box {
                Image(
                    painter = painterResource(place.bannerImageResourceId),
                    contentDescription = null,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, MaterialTheme.colorScheme.scrim),
                                0f,
                                400f
                            )
                        )
                ) {
                    Text(
                        text = stringResource(place.titleResourceId),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(R.dimen.padding_medium),
                            vertical = dimensionResource(R.dimen.padding_small)
                        )
                    )
                }
            }

            Text(
                text = stringResource(place.detailsResourceId),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.padding_detail_content_vertical),
                    horizontal = dimensionResource(R.dimen.padding_detail_content_horizontal)
                )
            )
        }
    }
}