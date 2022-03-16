package made.dicoding.moviecatalogueapps.core.domain.repository

import made.dicoding.moviecatalogueapps.core.model.Movies
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    suspend fun trendingMovies():Flow<Result<List<Movies>>>
    suspend fun trendingTvShows():Flow<Result<List<Movies>>>
    fun detailMovie(id:Int):Flow<Result<Movies>>
    fun detailTvShows(id:Int):Flow<Result<Movies>>
}