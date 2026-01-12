package com.example.mininavigationapp.di

import com.example.mininavigationapp.data.repository.PlaceRepositoryImpl
import com.example.mininavigationapp.domain.repository.PlaceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providePlaceRepository(
        impl: PlaceRepositoryImpl
    ): PlaceRepository = impl
}