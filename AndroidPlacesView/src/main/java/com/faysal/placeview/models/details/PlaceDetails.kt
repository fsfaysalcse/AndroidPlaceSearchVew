package com.faysal.placeview.models.details

data class PlaceDetails(
    val address_components: List<AddressComponent> = emptyList(),
    val adr_address: String = "",
    val formatted_address: String = "",
    val geometry: Geometry,
    val icon: String = "",
    val icon_background_color: String = "",
    val icon_mask_base_uri: String = "",
    val name: String = "",
    val photos: List<Photo> = emptyList(),
    val place_id: String = "",
    val reference: String = "",
    val types: List<String> = emptyList(),
    val url: String = "",
    val utc_offset: Int = 0,
    val vicinity: String = "",
    val website: String
)