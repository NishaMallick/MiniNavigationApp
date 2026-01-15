package com.example.mininavigationapp.presentation.details

import androidx.lifecycle.ViewModel
import com.example.mininavigationapp.domain.usecase.CalculateDistanceUseCase
import com.example.mininavigationapp.domain.usecase.GetPlacesByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlaceDetailsViewModel @Inject constructor(
    private val getPlacesByIdUseCase: GetPlacesByIdUseCase,
    private val calculateDistanceUseCase: CalculateDistanceUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<PlaceDetailsUiState>(PlaceDetailsUiState.Loading)
    val state: StateFlow<PlaceDetailsUiState> = _state

    private val mockedLocation = Pair(59.1925, 17.6270)

    fun loadPlace(placeId: Int) {
        val place = getPlacesByIdUseCase(placeId)
        if (place == null) {
            _state.value = PlaceDetailsUiState.Error("Place not found")
            return
        }

        val distance = calculateDistanceUseCase(
            mockedLocation.first,
            mockedLocation.second,
            place.lat,
            place.lng
        )

        _state.value = PlaceDetailsUiState.Success(place, distance)
    }
}