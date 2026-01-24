package com.example.mininavigationapp.presentation.details

import com.example.mininavigationapp.domain.model.Place

sealed interface PlaceDetailsUiState {
    object Loading : PlaceDetailsUiState
    data class Success(val places: Place, val distanceInKms: Double) : PlaceDetailsUiState
    data class Error(val message: String) : PlaceDetailsUiState
}