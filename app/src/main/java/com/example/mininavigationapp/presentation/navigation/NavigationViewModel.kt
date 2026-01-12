package com.example.mininavigationapp.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mininavigationapp.domain.usecase.SimulateNavigationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val simulateNavigationUseCase: SimulateNavigationUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<NavigationUiState>(NavigationUiState.Idle)
    val state: StateFlow<NavigationUiState> = _state

    private val mockedStartLat = 59.1925
    private val mockedStartLng = 17.6270

    fun startNavigation(destinationLat: Double, destinationLng: Double) {
        viewModelScope.launch {
            simulateNavigationUseCase(
                mockedStartLat,
                mockedStartLng,
                destinationLat,
                destinationLng
            ).collect {
                _state.value = it
            }
        }
    }
}