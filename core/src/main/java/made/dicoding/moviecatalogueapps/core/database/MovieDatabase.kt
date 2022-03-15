package made.dicoding.moviecatalogueapps.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper.DB_VERSION
import made.dicoding.moviecatalogueapps.core.data.remote.local.dao.FavoriteDao
import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity
import made.dicoding.moviecatalogueapps.core.database.converter.Converters

@Database(
    entities = [
        FavoriteEntity::class
    ]
, version = DB_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase:RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}