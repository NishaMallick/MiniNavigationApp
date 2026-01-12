package com.example.mininavigationapp.presentation.navigation

import android.R.attr.padding
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScreen(
    viewModel: NavigationViewModel,
    navController: NavController,
    destinationLat: Double,
    destinationLng: Double) {

    val state by viewModel.state.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF1565C0),
                titleContentColor = Color.White
            ),
            title = { Text("Navigation") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back",
                        tint = Color.White)
                }
            }
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            when (state) {
                NavigationUiState.Idle -> {
                    Text("Ready to start navigation")
                    Button(onClick = { viewModel.startNavigation(destinationLat, destinationLng ) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF009688))) {
                        Text("Start")
                    }
                }
                is NavigationUiState.Navigating -> {
                    val data = state as NavigationUiState.Navigating

                    Text(
                        text = "Remaining distance",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "%.2f km".format(data.remainingDistanceKm),
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "ETA: ${data.etaText}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    /*LinearProgressIndicator(
                        progress = (1f - data.remainingDistanceKm / data.initialDistanceKm)
                            .coerceIn(0f, 1f),
                        modifier = Modifier.fillMaxWidth()
                    )*/

//                    Row(
//                        horizontalArrangement = Arrangement.spacedBy(16.dp)
//                    ) {
//                        Button(onClick = { viewModel.stop() }) {
//                            Text("Stop")
//                        }
//                    }
                }

                NavigationUiState.Arrived -> {
                    Text("You have arrived.")
                }

                else -> {}
            }
        }

    }
}