package made.dicoding.moviecatalogueapps

import made.dicoding.moviecatalogueapps.core.domain.use_case.movies.MoviesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import made.dicoding.moviecatalogueapps.core.domain.repository.IFavoriteRepository
import made.dicoding.moviecatalogueapps.core.domain.repository.IMovieRepository
import made.dicoding.moviecatalogueapps.core.domain.use_case.movie.IMovieUseCase
import made.dicoding.moviecatalogueapps.core.domain.use_case.movie.MovieUseCaseImpl
import made.dicoding.moviecatalogueapps.core.domain.use_case.movies.IMoviesUseCase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideMoviesUseCase(
        movieRepository: IMovieRepository
    ): IMoviesUseCase = MoviesUseCaseImpl(movieRepository)

    @Singleton
    @Provides
    fun provideMovieUseCase(
        movieRepository: IMovieRepository,
        favoriteRepo: IFavoriteRepository
    ): IMovieUseCase = MovieUseCaseImpl(movieRepository,favoriteRepo)

}