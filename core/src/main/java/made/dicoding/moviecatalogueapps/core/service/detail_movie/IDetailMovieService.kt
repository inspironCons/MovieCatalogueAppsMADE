package made.dicoding.moviecatalogueapps.core.service.detail_movie

import made.dicoding.moviecatalogueapps.core.model.Movies
import kotlinx.coroutines.flow.Flow

interface IDetailMovieService {
    fun fetchDetailMovies(id:Int): Flow<Result<Movies>>
    fun fetchDetailTV(id:Int): Flow<Result<Movies>>
}