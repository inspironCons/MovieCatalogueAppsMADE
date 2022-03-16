package made.dicoding.moviecatalogueapps.core.data.repository_impl

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity
import made.dicoding.moviecatalogueapps.core.data.service.favorite.IFavoriteService
import made.dicoding.moviecatalogueapps.core.domain.repository.IFavoriteRepository
import javax.inject.Inject
import javax.inject.Singleton

class FavoriteRepositoryImpl @Inject constructor(
    private val service: IFavoriteService
): IFavoriteRepository {

    override fun getFavoriteByType(type: String): PagingSource<Int, FavoriteEntity> = service
        .getFavoriteByType(type)

    override fun isMovieAlreadyInFavorite(id: Int): Flow<Result<Boolean>>  = service
        .isMovieAlreadyInFavorite(id)

    override suspend fun insertMovie(movie: FavoriteEntity): Flow<Result<String>> = service
        .insertMovie(movie)

    override suspend fun deleteMovie(movie: FavoriteEntity): Flow<Result<String>> = service
        .deleteMovie(movie)
}