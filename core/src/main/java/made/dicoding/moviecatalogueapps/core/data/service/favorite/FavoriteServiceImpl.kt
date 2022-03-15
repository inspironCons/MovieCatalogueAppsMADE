package made.dicoding.moviecatalogueapps.core.data.service.favorite

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import made.dicoding.moviecatalogueapps.core.data.remote.local.dao.FavoriteDao
import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity
import java.io.IOException
import javax.inject.Inject

class FavoriteServiceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
): IFavoriteService {
    override fun getFavoriteByType(type: String): PagingSource<Int, FavoriteEntity> = favoriteDao.getMovies(type)

    override fun isMovieAlreadyInFavorite(id: Int): Flow<Result<Boolean>> = flow {
        emit(Result.success(favoriteDao.countMoviesById(id) > 0))
    }.catch {
        emit(Result.failure(RuntimeException("Terdapat kesalahan, silahkan coba lagi")))
    }

    override suspend fun insertMovie(movie: FavoriteEntity): Flow<Result<String>> = flow {
        emit(Result.success("Loading..."))
        favoriteDao.insert(movie)
        emit(Result.success("Data Berhasil Ditambahkan"))
    }.catch { e->
        if(e is IOException){
            emit(Result.failure(RuntimeException("Oppss.. ada kesalahan saat menambahkan data")))
        }else{
            emit(Result.failure(RuntimeException("Terdapat kesalahan, silahkan coba lagi")))
        }
    }

    override suspend fun deleteMovie(movie: FavoriteEntity): Flow<Result<String>> = flow {
        emit(Result.success("Loading..."))
        favoriteDao.delete(movie)
        emit(Result.success("Data Berhasil Dihapus"))
    }.catch { e->
        if(e is IOException) {
            emit(Result.failure(RuntimeException("Oppss.. ada kesalahan saat menghapus data")))
        }else {
            emit(Result.failure(RuntimeException("Terdapat kesalahan, silahkan coba lagi")))
        }

    }
}