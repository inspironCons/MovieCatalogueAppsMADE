package made.dicoding.moviecatalogueapps.core.data.service.favorite

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity
import made.dicoding.moviecatalogueapps.core.model.Movies

interface IFavoriteService {
    fun getFavoriteByType(type:String):PagingSource<Int, FavoriteEntity>
    fun isMovieAlreadyInFavorite(id:Int): Flow<Result<Boolean>>
    suspend fun insertMovie(movie: FavoriteEntity):Flow<Result<String>>
    suspend fun deleteMovie(movie: FavoriteEntity):Flow<Result<String>>
    fun getMovieById(id:Int):Flow<Result<FavoriteEntity>>
}