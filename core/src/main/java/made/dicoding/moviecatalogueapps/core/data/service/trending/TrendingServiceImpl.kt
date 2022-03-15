package made.dicoding.moviecatalogueapps.core.data.service.trending

import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper
import made.dicoding.moviecatalogueapps.core.data.remote.network.TrendingApi
import made.dicoding.moviecatalogueapps.core.model.Movies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrendingServiceImpl @Inject constructor(
    private val trendingApi: TrendingApi
): ITrendingService {
    override suspend fun fetchApiMovieList(): Flow<Result<List<Movies>>> {
        return flow{
            val request = trendingApi.getTrendingMovieOnThisWeek(
                ConstanNameHelper.API_KEY,
                ConstanNameHelper.LANGUAGE,
                ConstanNameHelper.REGIONS
            )
            emit(Result.success(request.toMovies()))
        }.catch {
            emit(Result.failure(RuntimeException("Connection Lost, check your connection")))
        }
    }

    override suspend fun fetchApiTvList(): Flow<Result<List<Movies>>> {
        return flow{
           
            val request = trendingApi.getTrendingTvShowsOnThisWeek(
                ConstanNameHelper.API_KEY,
                ConstanNameHelper.LANGUAGE,
                ConstanNameHelper.REGIONS
            )
            
            emit(Result.success(request.toMovies()))
        }.catch {
            
            emit(Result.failure(RuntimeException("Connection Lost, check your connection")))
        }
    }
}