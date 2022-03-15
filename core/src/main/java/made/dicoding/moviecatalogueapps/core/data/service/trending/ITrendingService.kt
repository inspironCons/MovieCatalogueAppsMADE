package made.dicoding.moviecatalogueapps.core.data.service.trending

import made.dicoding.moviecatalogueapps.core.model.Movies
import kotlinx.coroutines.flow.Flow

interface ITrendingService {
    suspend fun fetchApiMovieList(): Flow<Result<List<Movies>>>
    suspend fun fetchApiTvList(): Flow<Result<List<Movies>>>
}