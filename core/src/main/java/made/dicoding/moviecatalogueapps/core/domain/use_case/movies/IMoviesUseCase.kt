package made.dicoding.moviecatalogueapps.core.domain.use_case.movies

import kotlinx.coroutines.flow.Flow
import made.dicoding.moviecatalogueapps.core.model.ListMovies

interface IMoviesUseCase {
    suspend fun getMovieList(): Flow<Result<List<ListMovies>>>
    suspend fun getTvShowList(): Flow<Result<List<ListMovies>>>
}