package com.example.mininavigationapp.data.datasource

import com.example.mininavigationapp.domain.model.Place

fun PlaceDto.toDomain(): Place {
    return Place(
        id = id,
        name = name,
        icon = icon,
        lat = lat,
        lng = lng
    )
}