package made.dicoding.moviecatalogueapps.di

import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import made.dicoding.moviecatalogueapps.core.domain.repository.IFavoriteRepository
import made.dicoding.moviecatalogueapps.core.domain.repository.IMovieRepository
import made.dicoding.moviecatalogueapps.core.domain.use_case.favorite.FavoriteUseCaseImpl
import made.dicoding.moviecatalogueapps.core.domain.use_case.favorite.IFavoriteUseCase
import made.dicoding.moviecatalogueapps.core.domain.use_case.movie.IMovieUseCase
import made.dicoding.moviecatalogueapps.core.domain.use_case.movie.MovieUseCaseImpl
import made.dicoding.moviecatalogueapps.core.domain.use_case.movies.IMoviesUseCase
import made.dicoding.moviecatalogueapps.core.domain.use_case.movies.MoviesUseCaseImpl
import made.dicoding.moviecatalogueapps.core.utils.mapper.ListMoviesMapper
import made.dicoding.moviecatalogueapps.core.utils.mapper.MovieMapper
import javax.inject.Singleton

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppComponent{
    val favoriteUseCase:IFavoriteUseCase
}

@Module
@InstallIn(SingletonComponent::class)
class AppModule{
    @Provides
    @Singleton
    fun provideMoviesUseCase(repo:IMovieRepository,mapper:ListMoviesMapper):IMoviesUseCase = MoviesUseCaseImpl(repo,mapper)
    @Provides
    @Singleton
    fun provideMovieUseCase(movieRepo:IMovieRepository,favoRepo:IFavoriteRepository,mapper:MovieMapper): IMovieUseCase =
        MovieUseCaseImpl(movieRepo,favoRepo,mapper)
    @Provides
    @Singleton
    fun provideFavoriteUseCase(favorRepo: IFavoriteRepository):IFavoriteUseCase =
        FavoriteUseCaseImpl(favorRepo)
}