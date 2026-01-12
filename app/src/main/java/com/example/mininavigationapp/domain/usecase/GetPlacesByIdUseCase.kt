package com.example.mininavigationapp.domain.usecase

import com.example.mininavigationapp.domain.model.Place
import com.example.mininavigationapp.domain.repository.PlaceRepository
import javax.inject.Inject

class GetPlacesByIdUseCase @Inject constructor(
    private val repository: PlaceRepository) {

    suspend operator fun invoke(placeId: Int): Place? {
        return repository.getPlaceById(placeId)
    }
}