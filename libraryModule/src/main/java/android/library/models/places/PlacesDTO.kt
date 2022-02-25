package android.library.models.places

data class PlacesDTO(
    val results: List<Result> = emptyList(),
    val status: String = "",
    val error_message : String = ""
)