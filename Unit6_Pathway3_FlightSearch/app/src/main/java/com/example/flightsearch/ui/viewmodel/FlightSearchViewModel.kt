package com.example.flightsearch.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.data.FlightRepository
import com.example.flightsearch.data.UserPreferencesRepository
import com.example.flightsearch.ui.model.FlightRoute
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class FlightSearchUiState(
    val searchQuery: String = "",
    val suggestedAirports: List<Airport> = emptyList(),
    val selectedAirport: Airport? = null,
    val destinationFlights: List<FlightRoute> = emptyList(),
    val favoriteFlights: List<FlightRoute> = emptyList(),
    val isShowingSuggestions: Boolean = false
)

class FlightSearchViewModel(
    private val flightRepository: FlightRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _selectedAirport = MutableStateFlow<Airport?>(null)

    val uiState: StateFlow<FlightSearchUiState> = combine(
        _searchQuery,
        _selectedAirport,
        flightRepository.getAllFavorites()
    ) { query, selected, favorites ->
        Triple(query, selected, favorites)
    }.flatMapLatest { (query, selected, favorites) ->
        if (query.isEmpty()) {
            // Hiển thị danh sách yêu thích khi không có tìm kiếm
            flow {
                val favoriteFlights = favorites.map { fav ->
                    val departure = flightRepository.getAirportByCode(fav.departureCode)
                    val destination = flightRepository.getAirportByCode(fav.destinationCode)
                    FlightRoute(
                        departureCode = fav.departureCode,
                        departureName = departure?.name ?: "",
                        destinationCode = fav.destinationCode,
                        destinationName = destination?.name ?: "",
                        isFavorite = true
                    )
                }
                emit(
                    FlightSearchUiState(
                        searchQuery = query,
                        favoriteFlights = favoriteFlights,
                        isShowingSuggestions = false
                    )
                )
            }
        } else if (selected != null) {
            // Hiển thị các chuyến bay từ sân bay đã chọn
            flightRepository.getDestinationAirports(selected.iataCode).map { destinations ->
                val flights = destinations.map { dest ->
                    val isFav = favorites.any {
                        it.departureCode == selected.iataCode &&
                                it.destinationCode == dest.iataCode
                    }
                    FlightRoute(
                        departureCode = selected.iataCode,
                        departureName = selected.name,
                        destinationCode = dest.iataCode,
                        destinationName = dest.name,
                        isFavorite = isFav
                    )
                }
                FlightSearchUiState(
                    searchQuery = query,
                    selectedAirport = selected,
                    destinationFlights = flights,
                    isShowingSuggestions = false
                )
            }
        } else {
            // Hiển thị gợi ý tìm kiếm
            flightRepository.searchAirports(query).map { airports ->
                FlightSearchUiState(
                    searchQuery = query,
                    suggestedAirports = airports,
                    isShowingSuggestions = true
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FlightSearchUiState()
    )

    init {
        // Load saved search query
        viewModelScope.launch {
            userPreferencesRepository.searchQuery.collect { savedQuery ->
                if (_searchQuery.value.isEmpty() && savedQuery.isNotEmpty()) {
                    updateSearchQuery(savedQuery)
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        _selectedAirport.value = null

        viewModelScope.launch {
            userPreferencesRepository.saveSearchQuery(query)
        }
    }

    fun selectAirport(airport: Airport) {
        _selectedAirport.value = airport
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _selectedAirport.value = null

        viewModelScope.launch {
            userPreferencesRepository.saveSearchQuery("")
        }
    }

    fun toggleFavorite(departureCode: String, destinationCode: String) {
        viewModelScope.launch {
            val existing = flightRepository.getFavorite(departureCode, destinationCode).first()
            if (existing != null) {
                flightRepository.deleteFavorite(existing)
            } else {
                flightRepository.insertFavorite(
                    Favorite(
                        departureCode = departureCode,
                        destinationCode = destinationCode
                    )
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as FlightSearchApplication)
                FlightSearchViewModel(
                    flightRepository = application.flightRepository,
                    userPreferencesRepository = application.userPreferencesRepository
                )
            }
        }
    }
}