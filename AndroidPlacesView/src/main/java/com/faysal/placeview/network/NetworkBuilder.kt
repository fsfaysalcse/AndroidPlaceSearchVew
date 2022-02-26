package com.faysal.placeview.network

import com.faysal.placeview.utlity.Constants
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


object NetworkBuilder {

    fun providePlacesUrl(apiKey : String,query : String) : String {

        return Constants.PLACE_LIST_ENDPOINT.toHttpUrlOrNull()!!
            .newBuilder()
            .addQueryParameter("key", apiKey)
            .addQueryParameter("query", query)
            .build().toString()

    }

    fun provideDetailsUrl(apiKey : String,place_id : String) : String {
        return Constants.PLACE_DETAILS_ENDPOINT.toHttpUrlOrNull()!!
            .newBuilder()
            .addQueryParameter("key", apiKey)
            .addQueryParameter("place_id", place_id)
            .build().toString()

    }
    fun provideRequestBuilder(rawUrl : String): Response {
        val request: Request = Request.Builder().url(rawUrl).build()
        return OkHttpClient()
            .newCall(request)
            .execute()
    }

}

