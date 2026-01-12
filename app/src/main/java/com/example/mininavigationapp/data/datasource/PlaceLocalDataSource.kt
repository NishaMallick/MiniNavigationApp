package com.example.mininavigationapp.data.datasource

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PlaceLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun loadPlaces(): List<PlaceDto> {
        val json = context.assets
            .open("places.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<List<PlaceDto>>() {}.type
        return Gson().fromJson(json, type)
    }
}
