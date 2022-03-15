package made.dicoding.moviecatalogueapps.core.domain.use_case.movie

import kotlinx.coroutines.flow.Flow
import made.dicoding.moviecatalogueapps.core.model.DetailMovie

interface IMovieUseCase {
    suspend fun getDetailMovie(id:Int):Flow<Result<DetailMovie>>
    suspend fun getDetailTvShow(id:Int):Flow<Result<DetailMovie>>
    fun isMovieAlreadyInFavorite(id:Int):Flow<Result<Boolean>>
}