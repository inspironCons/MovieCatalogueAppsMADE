package made.dicoding.moviecatalogueapps.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import made.dicoding.moviecatalogueapps.core.data.repository_impl.FavoriteRepositoryImpl
import made.dicoding.moviecatalogueapps.core.data.repository_impl.MovieRepositoryImpl
import made.dicoding.moviecatalogueapps.core.data.service.detail_movie.IDetailMovieService
import made.dicoding.moviecatalogueapps.core.data.service.favorite.IFavoriteService
import made.dicoding.moviecatalogueapps.core.data.service.trending.ITrendingService
import made.dicoding.moviecatalogueapps.core.domain.repository.IFavoriteRepository
import made.dicoding.moviecatalogueapps.core.domain.repository.IMovieRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMovieRepository(
        iTrendingService: ITrendingService,
        iDetailMovieService: IDetailMovieService
    ):IMovieRepository = MovieRepositoryImpl(iTrendingService,iDetailMovieService)

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        iFavoriteService: IFavoriteService,
    ):IFavoriteRepository = FavoriteRepositoryImpl(iFavoriteService)
}