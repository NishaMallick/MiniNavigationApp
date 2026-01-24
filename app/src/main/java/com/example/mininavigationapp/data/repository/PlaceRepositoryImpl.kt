package com.example.mininavigationapp.data.repository

import com.example.mininavigationapp.data.datasource.PlaceLocalDataSource
import com.example.mininavigationapp.data.datasource.toDomain
import com.example.mininavigationapp.domain.model.Place
import com.example.mininavigationapp.domain.repository.PlaceRepository
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val localDataSource: PlaceLocalDataSource
) : PlaceRepository {

    override fun getPlaces(): List<Place> {
        return localDataSource
            .loadPlaces()
            .map { it.toDomain() }
    }

    override fun getPlaceById(id: Int): Place? {
        return localDataSource
            .loadPlaces()
            .find { it.id == id }?.toDomain()
    }
}
