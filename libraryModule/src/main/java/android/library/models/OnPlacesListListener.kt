package android.library.models

import android.library.models.places.Places

interface OnPlacesListListener {
    /**
     * Triggered when the places details are fetched and returns the details of the pace
     */
    fun onSuccess(placesList : List<Places>)

    /**
     * Triggered when there is an error and passes the error message along as a parameter
     */
    fun onError(errorMessage: String)
}