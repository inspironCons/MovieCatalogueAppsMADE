package made.dicoding.moviecatalogueapps.core.data.remote.network

import made.dicoding.moviecatalogueapps.core.data.remote.network.dto.DetailMoviesResponse
import made.dicoding.moviecatalogueapps.core.data.remote.network.dto.DetailTvShowsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailMoviesApi {
    @GET("movie/{id}")
    suspend fun getDetailMovie(
        @Path("id") id:Int,
        @Query("api_key") apiKey:String,
        @Query("language")language:String,
    ): DetailMoviesResponse

    @GET("tv/{id}")
    suspend fun getDetailTv(
        @Path("id") id:Int,
        @Query("api_key") apiKey:String,
        @Query("language")language:String,
    ): DetailTvShowsResponse
}