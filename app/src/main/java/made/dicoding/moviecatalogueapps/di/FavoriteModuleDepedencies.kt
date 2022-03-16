package made.dicoding.moviecatalogueapps.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import made.dicoding.moviecatalogueapps.core.domain.use_case.favorite.IFavoriteUseCase
import made.dicoding.moviecatalogueapps.core.domain.use_case.movie.IMovieUseCase
import made.dicoding.moviecatalogueapps.core.domain.use_case.movies.IMoviesUseCase

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDepedencies {
    fun favoriteUseCase():IFavoriteUseCase
    fun moviesUseCase():IMoviesUseCase
    fun movieUseCase():IMovieUseCase
}