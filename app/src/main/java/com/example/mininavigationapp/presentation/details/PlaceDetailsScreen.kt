package com.example.mininavigationapp.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mininavigationapp.R

@Composable
fun PlaceDetailsScreen(
    viewModel: PlaceDetailsViewModel,
    navController: NavController
) {

    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            PlaceDetailsTopBar(
                state = state,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { padding ->
        when (state) {
            is PlaceDetailsUiState.Loading -> {
                Text("Loading...")
            }

            is PlaceDetailsUiState.Success -> {
                val successState = state as PlaceDetailsUiState.Success
                //val distanceInKm = (state as? PlaceDetailsUiState.Success)?.distanceInKms
                PlaceDetailsSuccessContent(
                    state = successState,
                    modifier = Modifier.padding(padding),
                    onNavigateClick = {
                        navController.navigate(
                            "simulation/${successState.places.lat}/${successState.places.lng}"
                        )
                    }
                )
            }

            is PlaceDetailsUiState.Error -> {
                Text(
                    text = stringResource(R.string.error_text),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailsTopBar(
    state: PlaceDetailsUiState,
    onBackClick: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1565C0),
            titleContentColor = White
        ),
        title = {
            Text(
                when (state) {
                    is PlaceDetailsUiState.Success -> state.places.name
                    is PlaceDetailsUiState.Error -> "Error"
                    PlaceDetailsUiState.Loading -> "Loading..."
                }
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = White
                )
            }
        }
    )
}

@Composable
fun PlaceDetailsSuccessContent(
    state: PlaceDetailsUiState.Success,
    modifier: Modifier = Modifier,
    onNavigateClick: () -> Unit
) {
    val place = state.places
    val distanceInKm = state.distanceInKms

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.map_placeholder),
                contentDescription = "Map preview",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.coordinates_title_text),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = stringResource(
                        R.string.coordinates_text,
                        place.lat,
                        place.lng
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Distance : %.2f km".format(distanceInKm),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onNavigateClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.navigation_button))
        }
    }
}