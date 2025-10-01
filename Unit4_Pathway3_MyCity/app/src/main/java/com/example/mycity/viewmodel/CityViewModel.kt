package com.example.mycity.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mycity.data.LocalCityDataProvider
import com.example.mycity.model.Category
import com.example.mycity.model.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MyCityUiState(
    val categoriesList: List<Category> = emptyList(),
    val currentCategory: Category? = null,
    val placesForCategory: List<Place> = emptyList(),
    val currentPlace: Place? = null,
    val isShowingListPage: Boolean = true,
    val isLoading: Boolean = false
)

class CityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        MyCityUiState(
            categoriesList = LocalCityDataProvider.getCategories(),
            currentCategory = null,
            placesForCategory = emptyList(),
            currentPlace = null,
            isShowingListPage = true
        )
    )
    val uiState: StateFlow<MyCityUiState> = _uiState.asStateFlow()

    fun updateCurrentCategory(category: Category) {
        _uiState.update { currentState ->
            currentState.copy(
                currentCategory = category,
                placesForCategory = LocalCityDataProvider.getPlacesForCategory(category.id),
                currentPlace = null // Reset place when category changes
            )
        }
    }

    fun updateCurrentCategoryById(categoryId: Int) {
        val category = LocalCityDataProvider.getCategories().find { it.id == categoryId }
        category?.let {
            updateCurrentCategory(it)
        }
    }

    fun updateCurrentPlace(place: Place) {
        _uiState.update { currentState ->
            currentState.copy(currentPlace = place)
        }
    }

    fun updateCurrentPlaceById(placeId: Int) {
        val place = LocalCityDataProvider.getPlaceById(placeId)
        place?.let {
            updateCurrentPlace(it)
        }
    }

    fun clearCurrentPlace() {
        _uiState.update { currentState ->
            currentState.copy(currentPlace = null)
        }
    }
}