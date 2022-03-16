package made.dicoding.moviecatalogueapps.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import made.dicoding.moviecatalogueapps.core.domain.use_case.favorite.FavoriteUseCaseImpl
import made.dicoding.moviecatalogueapps.core.domain.use_case.favorite.IFavoriteUseCase
import made.dicoding.moviecatalogueapps.core.domain.use_case.movie.IMovieUseCase
import made.dicoding.moviecatalogueapps.core.domain.use_case.movie.MovieUseCaseImpl
import made.dicoding.moviecatalogueapps.core.domain.use_case.movies.IMoviesUseCase
import made.dicoding.moviecatalogueapps.core.domain.use_case.movies.MoviesUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideMoviesUsCase(moviesUseCaseImpl: MoviesUseCaseImpl): IMoviesUseCase

    @Binds
    @Singleton
    abstract fun provideMovieUsCase(movieUseCaseImpl: MovieUseCaseImpl):IMovieUseCase

    @Binds
    @Singleton
    abstract fun provideFavoriteUsCase(favoriteUseCaseImpl: FavoriteUseCaseImpl):IFavoriteUseCase
}