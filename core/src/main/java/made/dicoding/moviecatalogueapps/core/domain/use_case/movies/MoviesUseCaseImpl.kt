package made.dicoding.moviecatalogueapps.core.domain.use_case.movies

import kotlinx.coroutines.flow.*
import made.dicoding.moviecatalogueapps.core.domain.repository.IMovieRepository
import made.dicoding.moviecatalogueapps.core.model.ListMovies
import javax.inject.Inject

class MoviesUseCaseImpl @Inject constructor(
    private val repository: IMovieRepository
): IMoviesUseCase {
    override suspend fun getMovieList(): Flow<Result<List<ListMovies>>> {
        return repository.trendingMovies().map { result->
            result.map { movies-> movies.map { it.toListMovies() } }
        }
    }

    override suspend fun getTvShowList(): Flow<Result<List<ListMovies>>>{
        return repository.trendingTvShows().map { result->
            result.map { movies -> movies.map { it.toListMovies() } }
        }
    }
}