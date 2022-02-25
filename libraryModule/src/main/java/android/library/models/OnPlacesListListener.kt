package android.library.models

import android.library.models.details.PlaceDetails
import android.library.models.places.PlacesDTO
import android.library.models.places.Result

interface OnPlacesListListener {
    /**
     * Triggered when the places details are fetched and returns the details of the pace
     */
    fun onSuccess(placesList : List<Result>)

    /**
     * Triggered when there is an error and passes the error message along as a parameter
     */
    fun onError(errorMessage: String)
}