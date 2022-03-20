package made.dicoding.moviecatalogueapps.core.data.remote.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @JvmSuppressWildcards
    suspend fun insert(payload: FavoriteEntity)

    @Query("select * from favorite_movie where type=:type")
    fun getMovies(type:String):PagingSource<Int, FavoriteEntity>

    @Query("select count(*) from favorite_movie where id=:id")
    suspend fun countMoviesById(id:Int?):Int

    @Delete
    suspend fun delete(payload: FavoriteEntity)

    @Query("select * from favorite_movie where id=:id limit 1")
    suspend fun getDetailById(id:Int):FavoriteEntity
}