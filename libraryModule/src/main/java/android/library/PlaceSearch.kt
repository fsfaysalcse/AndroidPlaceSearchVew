package android.library

import android.content.Context
import android.library.models.OnPlaceDetailsListener
import android.library.models.details.PlaceDetailsDTO
import android.library.models.places.Result
import android.library.network.MapInterface
import android.library.network.NetworkBuilder
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "PlaceSearch"
class PlaceSearch(
    private val apiKey: String,
    private val context: Context
) {

    private val apiService: MapInterface by lazy {
        NetworkBuilder.provideMapAPI()
    }

    internal fun getPlaces(query: String) : Pair<List<Result>,String> {
        return try {
            val response = apiService.getPlacesList(apiKey, query).execute()
            val responseBody = response.body()
            if (response.isSuccessful && responseBody?.status == "OK"){
                Pair(responseBody.results,responseBody.error_message.toString())
            }else{
                Pair(emptyList(),responseBody?.error_message.toString())
            }
        }catch (e : Exception){
            Pair(emptyList(), e.message.toString() ?: "Failed something Wrong !")
        }
    }

    fun getPlaceDetails(placeID: String, listener: OnPlaceDetailsListener) {
        apiService.getPlaceDetails(apiKey, placeID).enqueue(object : Callback<PlaceDetailsDTO> {
            override fun onResponse(
                call: Call<PlaceDetailsDTO>,
                response: Response<PlaceDetailsDTO>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    listener.onPlaceDetails(response.body()!!.result)
                }else{
                    val errorResponse = response.errorBody().toString()
                    listener.onError(errorResponse?: "Failed something wrong !!")
                }
            }

            override fun onFailure(call: Call<PlaceDetailsDTO>, t: Throwable) {
                val errorMessage =  t.message
                listener.onError(errorMessage.toString())
            }

        })
    }

    data class Builder(
        private var apiKey: String = "",
    ) {
        /**
         * Sets the api key for the PlaceAPI
         */
        fun apiKey(apiKey: String) = apply { this.apiKey = apiKey }

        /**
         * Builds and creates an object of the PlaceAPI
         */
        fun build(context: Context) = PlaceSearch(apiKey, context)
    }


}



