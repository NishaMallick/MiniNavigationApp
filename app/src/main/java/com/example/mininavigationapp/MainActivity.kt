package com.example.mininavigationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mininavigationapp.presentation.details.PlaceDetailsScreen
import com.example.mininavigationapp.presentation.details.PlaceDetailsViewModel
import com.example.mininavigationapp.presentation.navigation.NavigationScreen
import com.example.mininavigationapp.presentation.navigation.NavigationViewModel
import com.example.mininavigationapp.presentation.places.PlacesScreen
import com.example.mininavigationapp.presentation.places.PlacesViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val placesViewModel: PlacesViewModel = hiltViewModel()

            NavHost(
                navController = navController,
                startDestination = "list"
            ) {
                //for home screen
                composable("list") {
                    PlacesScreen(
                        placesViewModel,
                        navController
                    )
                }

                //for place details screen
                composable(
                    route = "details/{id}",
                    arguments = listOf(
                        navArgument("id") { type = NavType.IntType }
                    )
                ) { backStackEntry ->

                    val id = backStackEntry.arguments?.getInt("id")!!
                    val detailsViewModel: PlaceDetailsViewModel = hiltViewModel()

                    LaunchedEffect(id) {
                        detailsViewModel.loadPlace(id)
                    }

                    PlaceDetailsScreen(detailsViewModel, navController)
                }

                //for navigation screen
                composable(
                    route = "simulation/{lat}/{lng}",
                    arguments = listOf(
                        navArgument("lat") { type = NavType.FloatType },
                        navArgument("lng") { type = NavType.FloatType }
                    )
                ) { backStackEntry ->
                    val lat = backStackEntry.arguments!!.getFloat("lat").toDouble()
                    val lng = backStackEntry.arguments!!.getFloat("lng").toDouble()

                    val navigationViewModel: NavigationViewModel = hiltViewModel()

                    NavigationScreen(
                        viewModel = navigationViewModel,
                        navController,
                        destinationLat = lat,
                        destinationLng = lng
                    )

                }
            }
        }
    }
}