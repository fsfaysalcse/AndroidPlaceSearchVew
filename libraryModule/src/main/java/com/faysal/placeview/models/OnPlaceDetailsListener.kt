package com.faysal.placeview.models

import com.faysal.placeview.models.details.PlaceDetails

interface OnPlaceDetailsListener {
    /**
     * Triggered when the places details are fetched and returns the details of the pace
     */
    fun onSuccess(placeDetails: PlaceDetails)

    /**
     * Triggered when there is an error and passes the error message along as a parameter
     */
    fun onError(errorMessage: String)
}