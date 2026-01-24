package com.example.mininavigationapp.presentation.navigation

sealed interface NavigationUiState {
    object Idle : NavigationUiState
    data class Navigating(
        val remainingDistanceKm: Double,
        val etaText: String
    ) : NavigationUiState

    object Arrived : NavigationUiState
    data class Error(val message: String) : NavigationUiState
}