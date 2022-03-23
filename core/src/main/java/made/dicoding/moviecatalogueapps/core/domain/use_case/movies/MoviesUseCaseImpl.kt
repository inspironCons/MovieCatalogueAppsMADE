package made.dicoding.moviecatalogueapps.core.domain.use_case.movies

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import made.dicoding.moviecatalogueapps.core.domain.repository.IMovieRepository
import made.dicoding.moviecatalogueapps.core.model.ListMovies
import made.dicoding.moviecatalogueapps.core.utils.mapper.ListMoviesMapper
import javax.inject.Inject

class MoviesUseCaseImpl @Inject constructor(
    private val repository: IMovieRepository,
    private val movieMapper:ListMoviesMapper
): IMoviesUseCase {
    override suspend fun getMovieList(): Flow<Result<List<ListMovies>>> = repository
        .trendingMovies().map {
            if(it.isSuccess){
                Result.success(movieMapper(it.getOrNull()))
            }else{
                Result.failure(it.exceptionOrNull()!!)
            }
        }

    override suspend fun getTvShowList(): Flow<Result<List<ListMovies>>> = repository
        .trendingTvShows().map {
            if(it.isSuccess){
                Result.success(movieMapper(it.getOrNull()))
            }else{
                Result.failure(it.exceptionOrNull()!!)
            }
        }
}