package com.example.mininavigationapp.presentation.places

import com.example.mininavigationapp.domain.model.Place

sealed interface PlacesUiState {
    object Loading : PlacesUiState
    data class Success(val places: List<Place>) : PlacesUiState
    data class Error(val message: String) : PlacesUiState
}