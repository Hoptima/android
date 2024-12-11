package id.hoptima.data.remote.response

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(
    @field:SerializedName("error")
    val error: String?,

    @field:SerializedName("recommendations")
    val recommendations: List<Property>?
)

data class Property(
    @field:SerializedName("Judul")
    val name: String,

    @field:SerializedName("Lokasi")
    val location: String,

    @field:SerializedName("Harga")
    val price: Long,

    @field:SerializedName("Deskripsi")
    val description: String? = null,

    @field:SerializedName("Kamar")
    val bedrooms: Int,

    @field:SerializedName("WC")
    val toilets: Int,

    @field:SerializedName("Parkir")
    val garages: Int,

    @field:SerializedName("Luas_Tanah")
    val landArea: Double,

    @field:SerializedName("Luas_Bangunan")
    val buildingArea: Double,

    @field:SerializedName("Image_Link")
    val imageUrl: String,

    @field:SerializedName("Property_Link")
    val url: String,
)
