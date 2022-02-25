package com.faysal.placeview.network

import com.faysal.placeview.models.details.PlaceDetailsDTO
import com.faysal.placeview.models.places.PlacesDTO
import com.faysal.placeview.utlity.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MapServiceApi {

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