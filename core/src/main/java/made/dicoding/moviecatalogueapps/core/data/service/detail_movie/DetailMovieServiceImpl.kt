package made.dicoding.moviecatalogueapps.core.data.service.detail_movie

import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper
import made.dicoding.moviecatalogueapps.core.data.remote.network.DetailMoviesApi
import made.dicoding.moviecatalogueapps.core.model.Movies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailMovieServiceImpl @Inject constructor(
    private val detailMoviesApi: DetailMoviesApi
): IDetailMovieService {
    override suspend fun fetchDetailMovies(id:Int): Flow<Result<Movies>> {
        return flow {
            val detail = detailMoviesApi.getDetailMovie(
                id,
                ConstanNameHelper.API_KEY,
                ConstanNameHelper.LANGUAGE
            )
            emit(Result.success(detail.toMovies()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

    override suspend fun fetchDetailTV(id:Int): Flow<Result<Movies>> {
        return flow {
            val detail = detailMoviesApi.getDetailTv(
                id,
                ConstanNameHelper.API_KEY,
                ConstanNameHelper.LANGUAGE
            )
            emit(Result.success(detail.toMovies()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}