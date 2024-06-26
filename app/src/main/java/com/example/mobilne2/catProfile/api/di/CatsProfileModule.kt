package com.example.mobilne2.catProfile.api.di

import com.example.mobilne2.catProfile.api.CatProfileApi
import com.example.mobilne2.networking.CatApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CatsProfileModule {
    @Provides
    @Singleton
    fun provideCatProfileApi(@CatApiClient retrofit: Retrofit): CatProfileApi = retrofit.create()
}