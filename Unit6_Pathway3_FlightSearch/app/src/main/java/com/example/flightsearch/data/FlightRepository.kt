package com.example.flightsearch.data

import kotlinx.coroutines.flow.Flow

class FlightRepository(private val flightDao: FlightDao) {

    fun searchAirports(query: String): Flow<List<Airport>> =
        flightDao.searchAirports(query)

    fun getDestinationAirports(departureCode: String): Flow<List<Airport>> =
        flightDao.getDestinationAirports(departureCode)

    suspend fun getAirportByCode(iataCode: String): Airport? =
        flightDao.getAirportByCode(iataCode)

    fun getAllFavorites(): Flow<List<Favorite>> =
        flightDao.getAllFavorites()

    fun getFavorite(departureCode: String, destinationCode: String): Flow<Favorite?> =
        flightDao.getFavorite(departureCode, destinationCode)

    suspend fun insertFavorite(favorite: Favorite) {
        flightDao.insertFavorite(favorite)
    }

    suspend fun deleteFavorite(favorite: Favorite) {
        flightDao.deleteFavorite(favorite)
    }

    suspend fun deleteFavoriteByCode(departureCode: String, destinationCode: String) {
        flightDao.deleteFavoriteByCode(departureCode, destinationCode)
    }
}