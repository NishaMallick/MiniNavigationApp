package com.example.mininavigationapp.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mininavigationapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScreen(
    viewModel: NavigationViewModel,
    navController: NavController,
    destinationLat: Double,
    destinationLng: Double
) {

    val state by viewModel.state.collectAsState()

    Scaffold(topBar = {
        NavigationTopBar(
            onBackClick = { navController.popBackStack() }
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                NavigationUiState.Idle -> {
                    NavigationIdleContent(
                        onStartClick = {
                            viewModel.startNavigation(destinationLat, destinationLng)
                        }
                    )
                }

                is NavigationUiState.Navigating -> {
                    NavigationInProgressContent(
                        state = state as NavigationUiState.Navigating,
                        onStopClick = { navController.popBackStack() }
                    )
                }

                NavigationUiState.Arrived -> {
                    Text(text = stringResource(R.string.arrived_text))
                }

                else -> Unit
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationTopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1565C0),
            titleContentColor = Color.White
        ),
        title = { Text(text = stringResource(R.string.navigation_text)) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun NavigationIdleContent(
    onStartClick: () -> Unit
) {
    Text(text = stringResource(R.string.navigation_start_text))

    Button(
        onClick = onStartClick,
        colors = ButtonDefaults.buttonColors(Color(0xFF2196F3))
    ) {
        Text(text = stringResource(R.string.navigation_start_button))
    }
}

@Composable
fun NavigationInProgressContent(
    state: NavigationUiState.Navigating,
    onStopClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.map_route_placeholder),
            contentDescription = "Map preview",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }

    Spacer(modifier = Modifier.height(24.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.remaining_distance_title_text),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Text(
            text = stringResource(
                R.string.distance_km,
                state.remainingDistanceKm
            ),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(
                R.string.eta_text,
                state.etaText
            ),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onStopClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFD32F2F)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = stringResource(R.string.navigation_stop_button),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

