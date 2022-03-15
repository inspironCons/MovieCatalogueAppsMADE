package made.dicoding.moviecatalogueapps.core.data.service.detail_movie

import made.dicoding.moviecatalogueapps.core.model.Movies
import kotlinx.coroutines.flow.Flow

interface IDetailMovieService {
    suspend fun fetchDetailMovies(id:Int): Flow<Result<Movies>>
    suspend fun fetchDetailTV(id:Int): Flow<Result<Movies>>
}