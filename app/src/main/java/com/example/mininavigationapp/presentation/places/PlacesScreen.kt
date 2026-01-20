package com.example.mininavigationapp.presentation.places

import android.annotation.SuppressLint
import com.example.mininavigationapp.R

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@SuppressLint("LocalContextResourcesRead")
@Composable
fun PlacesScreen(
    viewModel: PlacesViewModel,
    navController: NavController
                 ) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            PlacesTopBar()
        }
    ) { padding ->
        when (state) {
            is PlacesUiState.Success -> {
                LazyColumn(contentPadding = padding) {
                    items((state as? PlacesUiState.Success)?.places ?: emptyList()) {
                        val context = LocalContext.current
                        val iconResId = remember(it.icon) {
                            context.resources.getIdentifier(
                                it.icon,
                                "drawable",
                                context.packageName
                            )
                        }

                        Card(
                            modifier = Modifier.padding(2.dp)
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("details/${it.id}")
                                },
                            colors = CardDefaults.cardColors(Color.White),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                if (iconResId != 0) {
                                    Image(
                                        painter = painterResource(iconResId),
                                        contentScale = ContentScale.Fit,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .size(35.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        modifier = Modifier
                                            .padding(8.dp),
                                        text = it.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(8.dp),
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.sp,
                                        text = stringResource(R.string.lat_lng_text, it.lat, it.lng)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            else -> PlacesUiState.Error("Loading...")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesTopBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF1565C0)
        ),
        title = {
                Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(36.dp),
                )
        }
    )
}