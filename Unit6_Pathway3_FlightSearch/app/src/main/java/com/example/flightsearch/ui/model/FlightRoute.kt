package com.example.flightsearch.ui.model

data class FlightRoute(
    val departureCode: String,
    val departureName: String,
    val destinationCode: String,
    val destinationName: String,
    val isFavorite: Boolean = false
)