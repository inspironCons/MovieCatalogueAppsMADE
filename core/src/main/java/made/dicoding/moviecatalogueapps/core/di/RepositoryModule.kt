package made.dicoding.moviecatalogueapps.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import made.dicoding.moviecatalogueapps.core.data.repository_impl.FavoriteRepositoryImpl
import made.dicoding.moviecatalogueapps.core.data.repository_impl.MovieRepositoryImpl
import made.dicoding.moviecatalogueapps.core.data.service.detail_movie.IDetailMovieService
import made.dicoding.moviecatalogueapps.core.data.service.favorite.IFavoriteService
import made.dicoding.moviecatalogueapps.core.data.service.trending.ITrendingService
import made.dicoding.moviecatalogueapps.core.domain.repository.IFavoriteRepository
import made.dicoding.moviecatalogueapps.core.domain.repository.IMovieRepository

@Module(includes = [SourceModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ):IMovieRepository

    @Binds
    abstract fun provideFavoriteRepository(
        favoriteRepositoryImpl: FavoriteRepositoryImpl
    ):IFavoriteRepository
}