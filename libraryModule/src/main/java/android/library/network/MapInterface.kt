package android.library.network

import android.library.models.details.PlaceDetailsDTO
import android.library.models.places.PlacesDTO
import android.library.utlity.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MapInterface {

    @GET(Constants.PLACE_LIST_ENDPOINT)
    fun getPlacesList(
        @Query("key") map_api_key : String,
        @Query("query") query : String
    ) : Call<PlacesDTO>


    @GET(Constants.PLACE_DETAILS_ENDPOINT)
    fun getPlaceDetails(
        @Query("key") map_api_key : String,
        @Query("place_id") place_id : String
    ) : Call<PlaceDetailsDTO>
}