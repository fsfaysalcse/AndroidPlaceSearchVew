package com.faysal.placeview.network

import com.faysal.placeview.utlity.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkBuilder {
    fun provideMapAPI(): MapServiceApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MapServiceApi::class.java)
}