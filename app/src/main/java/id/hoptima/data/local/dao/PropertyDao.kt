package id.hoptima.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import id.hoptima.data.local.entity.PropertyEntity

@Dao
interface PropertyDao {
    @Query("SELECT * FROM properties")
    fun getProperties(): PagingSource<Int, PropertyEntity>

    @Query("SELECT * FROM properties ORDER BY created_at DESC LIMIT :limit")
    fun getRecentProperties(limit: Int = 5): LiveData<List<PropertyEntity>>

    @Query("SELECT * FROM properties WHERE bookmarked_at IS NOT NULL ORDER BY bookmarked_at DESC")
    fun getBookmarkedProperties(): PagingSource<Int, PropertyEntity>

    @Query("SELECT * FROM properties WHERE id = :id")
    fun getPropertyById(id: Int): LiveData<PropertyEntity?>

    @Insert
    suspend fun insertProperties(properties: List<PropertyEntity>)

    @Update
    suspend fun updateProperty(property: PropertyEntity)

    @Query(
        """
        DELETE FROM properties
        WHERE id NOT IN (
            SELECT id
            FROM properties
            WHERE bookmarked_at IS NOT NULL
            OR id IN (
                SELECT id
                FROM properties
                ORDER BY created_at DESC
                LIMIT 1000
            )
        )
    """
    )
    suspend fun clearProperties()
}