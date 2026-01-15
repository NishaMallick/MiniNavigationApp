package com.example.mininavigationapp.domain.repository

import com.example.mininavigationapp.domain.model.Place

interface PlaceRepository {
     fun getPlaces(): List<Place>

     fun getPlaceById(id: Int): Place?
}
