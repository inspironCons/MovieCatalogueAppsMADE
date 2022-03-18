package made.dicoding.moviecatalogueapps.core.domain.use_case.movie

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity
import made.dicoding.moviecatalogueapps.core.domain.repository.IFavoriteRepository
import made.dicoding.moviecatalogueapps.core.domain.repository.IMovieRepository
import made.dicoding.moviecatalogueapps.core.model.DetailMovie
import made.dicoding.moviecatalogueapps.core.model.Movies
import javax.inject.Inject

class MovieUseCaseImpl @Inject constructor(
    private val movieRepo: IMovieRepository,
    private val favoriteRepo: IFavoriteRepository
): IMovieUseCase {
    override fun getDetailMovie(id:Int): Flow<Result<DetailMovie>> = movieRepo
        .detailMovie(id).map { result->
        result.map { it.toDetailMovie() }
    }

    override fun getDetailTvShow(id:Int): Flow<Result<DetailMovie>> = movieRepo
        .detailTvShows(id).map { result->
        result.map { it.toDetailMovie() }
    }

    override fun getMovieFromSourceLocal(id: Int): Flow<Result<Movies>> = favoriteRepo
        .favoriteMovieById(id)

    override fun isMovieAlreadyInFavorite(id: Int): Flow<Result<Boolean>> = favoriteRepo
        .isMovieAlreadyInFavorite(id)

    override suspend fun insertMovieToFavorite(movie: DetailMovie): Flow<Result<String>> = favoriteRepo
        .insertMovie(movie.toMovie())

    override suspend fun deleteMovieFromFavorite(movie: DetailMovie): Flow<Result<String>> = favoriteRepo
        .deleteMovie(movie.toMovie())

}