package made.dicoding.moviecatalogueapps.core.data.repository_impl

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity
import made.dicoding.moviecatalogueapps.core.service.favorite.IFavoriteService
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

    override fun favoriteMovieById(id: Int): Flow<Result<Movies>> = service
        .getMovieById(id).map { result->
            result.map { it.toMovies() }
        }
}