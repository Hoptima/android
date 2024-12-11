package id.hoptima.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import id.hoptima.data.Result
import id.hoptima.data.local.AppDatabase
import id.hoptima.data.local.entity.PropertyEntity
import id.hoptima.data.remote.response.Property
import id.hoptima.data.remote.service.RecommendationService
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PropertyRepository @Inject constructor(
    private val recommendationService: RecommendationService,
    private val database: AppDatabase
) {
    private val propertyDao = database.propertyDao()

    fun getRecommendations(query: String) = liveData {
        emit(Result.Loading)

        try {
            val payload = mapOf("query" to query)
            val response = recommendationService.getRecommendations(payload)

            if (response.recommendations == null) {
                emit(Result.Error("Failed to get recommendations"))
                Log.e(TAG, "getRecommendations: ${response.error}")
            } else {
                val recommendations = response.recommendations.take(25).map { toPropertyEntity(it) }
                propertyDao.clearProperties()
                propertyDao.insertProperties(recommendations)

                val localData: LiveData<Result<List<PropertyEntity>>> =
                    propertyDao.getRecentProperties(recommendations.size).map { Result.Success(it) }
                emitSource(localData)
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An error occurred"))
            Log.e(TAG, "getRecommendations: ${e.message}")
        }
    }

    fun getProperties() = Pager(PagingConfig(pageSize = 25)) {
        database.propertyDao().getProperties()
    }.liveData

    fun getRecentProperties() = propertyDao.getRecentProperties()

    fun getBookmarkedProperties() = Pager(PagingConfig(pageSize = 25)) {
        propertyDao.getBookmarkedProperties()
    }.liveData

    fun getPropertyById(id: Int) = propertyDao.getPropertyById(id)

    suspend fun setPropertyBookmark(property: PropertyEntity, isBookmarked: Boolean) {
        val clone = property.copy(bookmarkedAt = if (isBookmarked) Date() else null)
        propertyDao.updateProperty(clone)
    }

    private fun toPropertyEntity(property: Property) = PropertyEntity(
        id = null,
        name = property.name,
        location = property.location,
        price = property.price,
        description = property.description,
        bedrooms = property.bedrooms,
        toilets = property.toilets,
        garages = property.garages,
        landArea = property.landArea,
        buildingArea = property.buildingArea,
        imageUrl = property.imageUrl,
        url = property.url,
        createdAt = Date()
    )

    companion object {
        private val TAG = PropertyRepository::class.java.simpleName
    }
}