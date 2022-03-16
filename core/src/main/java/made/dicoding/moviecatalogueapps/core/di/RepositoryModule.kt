package made.dicoding.moviecatalogueapps.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import made.dicoding.moviecatalogueapps.core.data.repository_impl.FavoriteRepositoryImpl
import made.dicoding.moviecatalogueapps.core.data.repository_impl.MovieRepositoryImpl
import made.dicoding.moviecatalogueapps.core.domain.repository.IFavoriteRepository
import made.dicoding.moviecatalogueapps.core.domain.repository.IMovieRepository
import made.dicoding.moviecatalogueapps.core.domain.use_case.favorite.FavoriteUseCaseImpl
import made.dicoding.moviecatalogueapps.core.domain.use_case.favorite.IFavoriteUseCase
import javax.inject.Singleton

@Module(includes = [
    DatabaseModule::class,
    NetworkModule::class,
    ServiceModule::class
])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ):IMovieRepository

    @Binds
    @Singleton
    abstract fun provideFavoriteRepository(
        favoriteRepositoryImpl: FavoriteRepositoryImpl
    ):IFavoriteRepository
}