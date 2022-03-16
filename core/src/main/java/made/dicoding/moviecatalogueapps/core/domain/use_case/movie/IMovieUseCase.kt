package made.dicoding.moviecatalogueapps.core.domain.use_case.movie

import kotlinx.coroutines.flow.Flow
import made.dicoding.moviecatalogueapps.core.model.DetailMovie

interface IMovieUseCase {
    fun getDetailMovie(id:Int):Flow<Result<DetailMovie>>
    fun getDetailTvShow(id:Int):Flow<Result<DetailMovie>>
    fun isMovieAlreadyInFavorite(id:Int):Flow<Result<Boolean>>
    suspend fun insertMovieToFavorite(movie:DetailMovie):Flow<Result<String>>
    suspend fun deleteMovieFromFavorite(movie:DetailMovie):Flow<Result<String>>

}