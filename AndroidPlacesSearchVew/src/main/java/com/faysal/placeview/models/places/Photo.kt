package com.faysal.placeview.models.places

data class Photo(
    val height: Int,
    val html_attributions: List<String> = emptyList(),
    val photo_reference: String,
    val width: Int
)