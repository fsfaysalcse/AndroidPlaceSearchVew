package android.library.models.places

data class PlacesDTO(
    val results: List<Places> = emptyList(),
    val status: String = "",
    val error_message : String = ""
)