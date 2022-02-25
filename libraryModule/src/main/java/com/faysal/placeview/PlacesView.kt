package com.faysal.placeview

import android.content.Context
import android.library.R
import com.faysal.placeview.models.OnPlaceDetailsListener
import com.faysal.placeview.models.details.PlaceDetailsDTO
import com.faysal.placeview.models.places.Places
import com.faysal.placeview.network.MapServiceApi
import com.faysal.placeview.network.NetworkBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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


    private val apiService: MapServiceApi by lazy {
        NetworkBuilder.provideMapAPI()
    }

    internal fun getPlaces(query: String): Pair<List<Places>, String> {
        return try {
            val response = apiService.getPlacesList(apiKey, query).execute()
            val responseBody = response.body()
            if (response.isSuccessful && responseBody?.status == "OK") {
                Pair(responseBody.results, responseBody.error_message.toString())
            } else {
                Pair(emptyList(), responseBody?.error_message.toString())
            }
        } catch (e: Exception) {
            Pair(
                emptyList(),
                e.message.toString() ?: context.getStr(R.string.error_connecting_to_places_api)
            )
        }
    }

    fun getPlaceDetails(placeID: String, listener: OnPlaceDetailsListener) {
        apiService.getPlaceDetails(apiKey, placeID).enqueue(object : Callback<PlaceDetailsDTO> {
            override fun onResponse(
                call: Call<PlaceDetailsDTO>,
                response: Response<PlaceDetailsDTO>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    listener.onSuccess(response.body()!!.result)
                } else {
                    val errorResponse = response.errorBody().toString()
                    listener.onError(
                        errorResponse ?: context.getStr(R.string.error_connecting_to_places_api)
                    )
                }
            }
            override fun onFailure(call: Call<PlaceDetailsDTO>, t: Throwable) {
                val errorMessage = t.message
                listener.onError(errorMessage.toString()?: context.getStr(R.string.error_connecting_to_places_api))
            }
        })
    }


}



