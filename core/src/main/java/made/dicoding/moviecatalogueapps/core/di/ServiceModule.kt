package made.dicoding.moviecatalogueapps.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import made.dicoding.moviecatalogueapps.core.data.remote.local.dao.FavoriteDao
import made.dicoding.moviecatalogueapps.core.data.remote.network.DetailMoviesApi
import made.dicoding.moviecatalogueapps.core.data.remote.network.TrendingApi
import made.dicoding.moviecatalogueapps.core.data.service.detail_movie.DetailMovieServiceImpl
import made.dicoding.moviecatalogueapps.core.data.service.detail_movie.IDetailMovieService
import made.dicoding.moviecatalogueapps.core.data.service.favorite.FavoriteServiceImpl
import made.dicoding.moviecatalogueapps.core.data.service.favorite.IFavoriteService
import made.dicoding.moviecatalogueapps.core.data.service.trending.ITrendingService
import made.dicoding.moviecatalogueapps.core.data.service.trending.TrendingServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    fun provideTrendingService(
        trendingApi: TrendingApi
    ): ITrendingService = TrendingServiceImpl(trendingApi)

    @Provides
    fun provideDetailMovieService(
        detailMoviesApi: DetailMoviesApi
    ): IDetailMovieService = DetailMovieServiceImpl(detailMoviesApi)

    @Provides
    @Singleton
    fun provideFavoriteService(
        favoriteDao: FavoriteDao
    ): IFavoriteService = FavoriteServiceImpl(favoriteDao)
}