package made.dicoding.moviecatalogueapps.core.data.repository_impl

import made.dicoding.moviecatalogueapps.core.model.Movies
import made.dicoding.moviecatalogueapps.core.data.service.detail_movie.IDetailMovieService
import made.dicoding.moviecatalogueapps.core.data.service.trending.ITrendingService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import made.dicoding.moviecatalogueapps.core.domain.repository.IMovieRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val trendingService: ITrendingService,
    private val detailService: IDetailMovieService
): IMovieRepository {
    override suspend fun trendingMovies(): Flow<Result<List<Movies>>> =
        trendingService.fetchApiMovieList().map {
            if(it.isSuccess){
                Result.success(it.getOrNull() as List<Movies>)
            }else{
                Result.failure(it.exceptionOrNull()!!)
            }
        }
    override suspend fun trendingTvShows(): Flow<Result<List<Movies>>> =
        trendingService.fetchApiTvList().map {
            if (it.isSuccess) {
                Result.success(it.getOrNull() as List<Movies>)
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }

    override suspend fun detailMovie(id:Int): Flow<Result<Movies>> =
        detailService.fetchDetailMovies(id).map {
            if(it.isSuccess){
                Result.success(it.getOrNull() as Movies)
            }else{
                Result.failure(it.exceptionOrNull()!!)
            }
        }

    override suspend fun detailTvShows(id:Int): Flow<Result<Movies>> =
        detailService.fetchDetailTV(id).map {
            if(it.isSuccess){
                Result.success(it.getOrNull() as Movies)
            }else{
                Result.failure(it.exceptionOrNull()!!)
            }
        }

//    fun getDetailFromLocal(id:Int):Flow<Result<FavoriteEntity>>{
//        return flow {
//            val detail = favoriteDao.getDetailById(id)
//            emit(Result.success(detail))
//        }.catch {
//            emit(Result.failure(RuntimeException(it.localizedMessage)))
//        }
//    }
}