package made.dicoding.moviecatalogueapps.core.data.remote.network

import made.dicoding.moviecatalogueapps.core.data.remote.network.dto.TrendingMovieResponse
import made.dicoding.moviecatalogueapps.core.data.remote.network.dto.TrendingTvResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingApi {
    @GET("trending/movie/week")
    suspend fun getTrendingMovieOnThisWeek(
        @Query("api_key") apiKey:String,
        @Query("language")language:String,
        @Query("region")region:String
    ): TrendingMovieResponse

    @GET("trending/tv/week")
    suspend fun getTrendingTvShowsOnThisWeek(
        @Query("api_key") apiKey:String,
        @Query("language")language:String,
        @Query("region")region:String
    ): TrendingTvResponse
}