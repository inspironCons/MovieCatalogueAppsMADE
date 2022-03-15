package made.dicoding.moviecatalogueapps.core.di

import android.content.Context
import androidx.room.Room
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper
import made.dicoding.moviecatalogueapps.core.data.remote.local.dao.FavoriteDao
import made.dicoding.moviecatalogueapps.core.data.remote.network.DetailMoviesApi
import made.dicoding.moviecatalogueapps.core.data.remote.network.TrendingApi
import made.dicoding.moviecatalogueapps.core.data.service.detail_movie.DetailMovieServiceImpl
import made.dicoding.moviecatalogueapps.core.data.service.detail_movie.IDetailMovieService
import made.dicoding.moviecatalogueapps.core.data.service.favorite.FavoriteServiceImpl
import made.dicoding.moviecatalogueapps.core.data.service.favorite.IFavoriteService
import made.dicoding.moviecatalogueapps.core.data.service.trending.ITrendingService
import made.dicoding.moviecatalogueapps.core.data.service.trending.TrendingServiceImpl
import made.dicoding.moviecatalogueapps.core.database.MovieDatabase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SourceModule {
    private val networkFlipperPlugin = NetworkFlipperPlugin()

    @Singleton
    @Provides
    fun networkFlipperPlugin():NetworkFlipperPlugin = networkFlipperPlugin

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin))
        .build()

    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(ConstanNameHelper.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    fun movieApi(retrofit: Retrofit): TrendingApi = retrofit.create(TrendingApi::class.java)

    @Provides
    fun detailMoviesApi(retrofit: Retrofit): DetailMoviesApi = retrofit.create(DetailMoviesApi::class.java)

    @Singleton
    @Provides
    fun dbInstance(@ApplicationContext app: Context): MovieDatabase = Room
        .databaseBuilder(app, MovieDatabase::class.java, ConstanNameHelper.DB_NAME)
        .build()

    @Provides
    fun favoriteDao(db: MovieDatabase): FavoriteDao = db.favoriteDao()

    @Singleton
    @Provides
    fun provideTrendingService(
        api: TrendingApi
    ): ITrendingService = TrendingServiceImpl(api)

    @Singleton
    @Provides
    fun provideDetailMovieService(
        api: DetailMoviesApi
    ): IDetailMovieService = DetailMovieServiceImpl(api)

    @Singleton
    @Provides
    fun provideFavoriteService(
        dao: FavoriteDao
    ): IFavoriteService = FavoriteServiceImpl(dao)
}