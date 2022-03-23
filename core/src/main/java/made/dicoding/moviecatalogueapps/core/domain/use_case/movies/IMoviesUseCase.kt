package made.dicoding.moviecatalogueapps.core.domain.use_case.movies

import kotlinx.coroutines.flow.Flow
import made.dicoding.moviecatalogueapps.core.model.ListMovies
import made.dicoding.moviecatalogueapps.core.model.Movies

interface IMoviesUseCase {
    suspend fun getMovieList(): Flow<Result<List<ListMovies>>>
    suspend fun getTvShowList(): Flow<Result<List<ListMovies>>>
}