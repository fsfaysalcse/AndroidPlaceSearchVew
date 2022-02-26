package com.faysal.placeview

import android.content.Context
import android.library.R
import com.faysal.placeview.models.OnPlaceDetailsListener
import com.faysal.placeview.models.details.PlaceDetailsDTO
import com.faysal.placeview.models.places.Places
import com.faysal.placeview.models.places.PlacesDTO
import com.faysal.placeview.network.NetworkBuilder
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

private const val TAG = "PlaceSearch"

class PlacesView(
    private val apiKey: String,
    private val context: Context
) {


    data class Builder(
        private var apiKey: String = "",
    ) {
        /**
         * Sets the api key for the PlaceSearch
         */
        fun apiKey(apiKey: String) = apply { this.apiKey = apiKey }

        /**
         * Builds and creates an object of the PlaceSearch
         */
        fun build(context: Context) = PlacesView(apiKey, context)
    }

    fun Context.getStr(id: Int) = this.resources.getString(id)



    internal fun getPlaces(query: String): Pair<List<Places>, String> {

        return try {
            val request: Request = Request.Builder()
                .url(NetworkBuilder.providePlacesUrl(apiKey,query))
                .build()

            val response = OkHttpClient().newCall(request).execute()
            val responseBody = Gson().fromJson(response.body?.string(),PlacesDTO::class.java)
            if (response.isSuccessful && responseBody?.status == "OK") {
                Pair(responseBody.results,"")
            } else {
                Pair(emptyList(), responseBody?.error_message.toString())
            }
        } catch (e: Exception) {
            Pair(emptyList(),provideErrorMessage(e))
        }
    }

    fun getPlaceDetails(placeID: String, listener: OnPlaceDetailsListener) {
        try {
            val request: Request = Request.Builder()
                .url(NetworkBuilder.provideDetailsUrl(apiKey, placeID))
                .build()
            OkHttpClient().newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    val errorMessage = e.message
                    listener.onError(provideErrorMessage(errorMessage))
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    if (response.isSuccessful && response.body != null) {
                        val rawResponse = response.body!!.string()
                        val responseBody = Gson().fromJson(rawResponse, PlaceDetailsDTO::class.java)
                        if (responseBody.status == "OK") {
                            listener.onSuccess(responseBody.result)
                        } else {
                            val errorResponse = responseBody.error_message
                            listener.onError(provideErrorMessage(errorResponse))
                        }
                    } else {
                        listener.onError(context.getStr(R.string.error_connecting_to_places_api))
                    }
                }

            })
        } catch (e: Exception) {
            listener.onError(provideErrorMessage(e))
        }
    }

    private fun provideErrorMessage(e : Exception) : String {
        return e.message.toString() ?: context.getStr(R.string.error_connecting_to_places_api)
    }
    private fun provideErrorMessage(message  : String?) : String {
        return message ?: context.getStr(R.string.error_connecting_to_places_api)
    }

}



