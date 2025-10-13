package com.example.flightsearch

import android.app.Application
import com.example.flightsearch.data.FlightDatabase
import com.example.flightsearch.data.FlightRepository
import com.example.flightsearch.data.UserPreferencesRepository

class FlightSearchApplication : Application() {

    private val database by lazy { FlightDatabase.getDatabase(this) }

    val flightRepository by lazy { FlightRepository(database.flightDao()) }

    val userPreferencesRepository by lazy { UserPreferencesRepository(this) }
}