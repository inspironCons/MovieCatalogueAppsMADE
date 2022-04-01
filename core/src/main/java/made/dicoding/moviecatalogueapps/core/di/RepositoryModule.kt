@file:Suppress("unused")

package made.dicoding.moviecatalogueapps.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import made.dicoding.moviecatalogueapps.core.data.repository_impl.FavoriteRepositoryImpl
import made.dicoding.moviecatalogueapps.core.data.repository_impl.MovieRepositoryImpl
import made.dicoding.moviecatalogueapps.core.domain.repository.IFavoriteRepository
import made.dicoding.moviecatalogueapps.core.domain.repository.IMovieRepository

@Module(includes = [
    ServiceModule::class,
    DatabaseModule::class,
    NetworkModule::class
])
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