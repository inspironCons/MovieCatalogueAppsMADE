package made.dicoding.moviecatalogueapps.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper
import made.dicoding.moviecatalogueapps.core.data.remote.local.dao.FavoriteDao
import made.dicoding.moviecatalogueapps.core.database.MovieDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun dbInstance(@ApplicationContext app: Context): MovieDatabase = Room
        .databaseBuilder(app, MovieDatabase::class.java, ConstanNameHelper.DB_NAME)
        .build()

    @Provides
    fun favoriteDao(db: MovieDatabase): FavoriteDao = db.favoriteDao()
}