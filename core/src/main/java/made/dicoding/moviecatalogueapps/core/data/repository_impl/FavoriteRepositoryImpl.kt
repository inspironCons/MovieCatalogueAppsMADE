package made.dicoding.moviecatalogueapps.core.data.repository_impl

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity
import made.dicoding.moviecatalogueapps.core.data.service.favorite.IFavoriteService
import made.dicoding.moviecatalogueapps.core.domain.repository.IFavoriteRepository
import made.dicoding.moviecatalogueapps.core.model.Movies
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val service: IFavoriteService
): IFavoriteRepository {

    override fun getFavoriteByType(type: String): PagingSource<Int, FavoriteEntity> = service
        .getFavoriteByType(type)

    override fun isMovieAlreadyInFavorite(id: Int): Flow<Result<Boolean>>  = service
        .isMovieAlreadyInFavorite(id)

    override suspend fun insertMovie(movie: Movies): Flow<Result<String>> = service
        .insertMovie(movie.toFavoriteEntity())

    override suspend fun deleteMovie(movie: Movies): Flow<Result<String>> = service
        .deleteMovie(movie.toFavoriteEntity())
}