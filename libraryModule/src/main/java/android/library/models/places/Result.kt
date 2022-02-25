package android.library.models.places

data class Result(
    val formatted_address: String = "",
    val geometry: Geometry = Geometry(),
    val icon: String = "",
    val icon_background_color: String = "",
    val icon_mask_base_uri: String = "",
    val name: String = "",
    val photos: List<Photo> = emptyList(),
    val place_id: String = "",
    val reference: String = "",
    val types: List<String> = emptyList(),
    val isEmpty : Boolean = false
)