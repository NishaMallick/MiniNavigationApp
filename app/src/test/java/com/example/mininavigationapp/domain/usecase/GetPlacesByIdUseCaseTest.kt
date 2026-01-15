package com.example.mininavigationapp.domain.usecase

import com.example.mininavigationapp.domain.model.Place
import com.example.mininavigationapp.domain.repository.PlaceRepository
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test

class GetPlacesByIdUseCaseTest {
    private lateinit var mRepository: PlaceRepository
    private lateinit var mUseCase: GetPlacesByIdUseCase

    private val fakePlace = Place(
        id = 1,
        name = "Home",
        lat = 59.3293,
        lng = 18.0686,
        icon = "home"
    )

    @Before
    fun setup() {
        mRepository = mockk()
        mUseCase = GetPlacesByIdUseCase(mRepository)
    }

    @Test
    fun `returns place when repository has matching id`() {
        // GIVEN
        every { mRepository.getPlaceById(1) } returns fakePlace

        // WHEN
        val result = mUseCase(1)

        // THEN
        assertNotNull(result)
        assertEquals(fakePlace.id, result?.id)
        assertEquals(fakePlace.name, result?.name)
    }

    @Test
    fun `returns null when repository has no matching id`() {
        // GIVEN
        every { mRepository.getPlaceById(99) } returns null

        // WHEN
        val result = mUseCase(99)

        // THEN
        assertNull(result)
    }
}