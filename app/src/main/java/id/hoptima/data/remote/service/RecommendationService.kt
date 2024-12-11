package id.hoptima.data.remote.service

import id.hoptima.data.remote.response.RecommendationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RecommendationService {
    @POST("recommendations")
    suspend fun getRecommendations(@Body body: Map<String, String>): RecommendationResponse
}