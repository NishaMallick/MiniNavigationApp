package com.example.mininavigationapp.domain.model

data class Place(
    val id: Int,
    val name: String,
    val icon: String,
    val lat: Double,
    val lng: Double
)