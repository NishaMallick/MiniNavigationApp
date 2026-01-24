package com.example.mininavigationapp.domain.usecase

import com.example.mininavigationapp.presentation.navigation.NavigationUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SimulateNavigationUseCase @Inject constructor(
    private val calculateDistanceUseCase: CalculateDistanceUseCase
) {

    private val speedKmPerHour = 40.0
    private val arrivalThresholdKm = 0.02
    operator fun invoke(
        startLat: Double,
        startLng: Double,
        destinationLat: Double,
        destinationLng: Double
    ): Flow<NavigationUiState> = flow {
        var currentLat = startLat
        var currentLng = startLng

        while (true) {
            val remainingKm = calculateDistanceUseCase(
                currentLat,
                currentLng,
                destinationLat,
                destinationLng
            )

            if (remainingKm < arrivalThresholdKm) {
                emit(NavigationUiState.Arrived)
                break
            }

            val etaMinutes = (remainingKm / speedKmPerHour) * 60

            val etaText = if (etaMinutes < 1) {
                "${(etaMinutes * 60).toInt()} sec"
            } else {
                "${etaMinutes.toInt()} min"
            }

            emit(
                NavigationUiState.Navigating(
                    remainingDistanceKm = remainingKm,
                    etaText = etaText
                )
            )

            // simulate movement toward destination
            currentLat += (destinationLat - currentLat) * 0.02
            currentLng += (destinationLng - currentLng) * 0.02

            delay(1000)
        }
    }.catch { e ->
        emit(NavigationUiState.Error(e.message ?: "Navigation failed"))
    }
}