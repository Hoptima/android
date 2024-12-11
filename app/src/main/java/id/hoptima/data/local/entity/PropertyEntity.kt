package id.hoptima.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "properties")
data class PropertyEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey(autoGenerate = true)
    val id: Int?,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "location")
    val location: String,

    @field:ColumnInfo(name = "price")
    val price: Long,

    @field:ColumnInfo(name = "description")
    val description: String? = null,

    @field:ColumnInfo(name = "bedrooms")
    val bedrooms: Int,

    @field:ColumnInfo(name = "bathrooms")
    val toilets: Int,

    @field:ColumnInfo(name = "garages")
    val garages: Int,

    @field:ColumnInfo(name = "land_area")
    val landArea: Double,

    @field:ColumnInfo(name = "building_area")
    val buildingArea: Double,

    @field:ColumnInfo(name = "image_url")
    val imageUrl: String,

    @field:ColumnInfo(name = "url")
    val url: String,

    @field:ColumnInfo(name = "created_at")
    val createdAt: Date? = null,

    @field:ColumnInfo(name = "bookmarked_at")
    val bookmarkedAt: Date? = null,
)