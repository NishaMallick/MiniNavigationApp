package com.example.mininavigationapp.domain.usecase

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculateDistanceUseCaseTest {
    private lateinit var mCalculateDistanceUseCaseTest : CalculateDistanceUseCase

    @Before
    fun setup() {
        mCalculateDistanceUseCaseTest = CalculateDistanceUseCase()
    }

    @Test
    fun givenSameCoordinatesReturnsZeroDistance() {
        val distance = mCalculateDistanceUseCaseTest(
            fromLat = 59.3293,
            fromLong = 18.0686,
            toLat = 59.3293,
            toLong = 18.0686
        )

        assertEquals(0.0, distance, 0.0)
    }

    @Test
    fun givenDifferentCoordinatesReturnsPositiveDistance() {
        val distance = mCalculateDistanceUseCaseTest(
            fromLat = 59.3293,
            fromLong = 18.0686,
            toLat = 59.3340,
            toLong = 18.0667
        )

        assertTrue(distance > 0)
    }
}