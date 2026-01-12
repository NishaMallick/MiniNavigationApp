package com.example.mininavigationapp.domain.usecase

import android.location.Location
import javax.inject.Inject

class CalculateDistanceUseCase @Inject constructor() {
    operator fun invoke(
        fromLat: Double,
        fromLong: Double,
        toLat: Double,
        toLong: Double
    ): Double {


        val pointA = Location("A").apply {
            latitude = fromLat
            longitude = fromLong
        }

        val pointB = Location("B").apply {
            latitude = toLat
            longitude = toLong
        }

        val distanceInKms: Double = (pointA.distanceTo(pointB).toDouble())/1000

        return distanceInKms
    }
}
