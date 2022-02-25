package android.library.models.places

data class Places(
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

    /*These variable for handling library UI*/
    val isError : Boolean = false,
    val errorMessage : String = ""
)