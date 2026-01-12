package com.example.mininavigationapp.domain.repository

import com.example.mininavigationapp.domain.model.Place

interface PlaceRepository {
    suspend fun getPlaces(): List<Place>

    suspend fun getPlaceById(id: Int): Place?
}
