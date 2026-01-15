package com.example.mininavigationapp.domain.usecase

import com.example.mininavigationapp.domain.model.Place
import com.example.mininavigationapp.domain.repository.PlaceRepository
import javax.inject.Inject

class GetPlacesUseCase @Inject constructor(
    private val repository: PlaceRepository
) {
    operator fun invoke(): List<Place> {
        return repository.getPlaces()
    }
}
