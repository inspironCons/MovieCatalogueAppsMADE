package made.dicoding.moviecatalogueapps.core.service.detail_movie

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper
import made.dicoding.moviecatalogueapps.core.data.remote.network.DetailMoviesApi
import made.dicoding.moviecatalogueapps.core.model.Movies
import made.dicoding.moviecatalogueapps.core.utils.EspressoIdling
import javax.inject.Inject

class DetailMovieServiceImpl @Inject constructor(
    private val detailMoviesApi: DetailMoviesApi
): IDetailMovieService {
    override fun fetchDetailMovies(id:Int): Flow<Result<Movies>> {
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

    override fun fetchDetailTV(id:Int): Flow<Result<Movies>> {
        return flow {
            val detail = detailMoviesApi.getDetailTv(
                id,
                ConstanNameHelper.API_KEY,
                ConstanNameHelper.LANGUAGE
            )
            if(!EspressoIdling.getEspressoIdlingResource.isIdleNow){
                EspressoIdling.decrement()
            }
            emit(Result.success(detail.toMovies()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}