package made.dicoding.moviecatalogueapps.core.service.favorite

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import made.dicoding.moviecatalogueapps.core.data.remote.local.dao.FavoriteDao
import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity
import made.dicoding.moviecatalogueapps.core.utils.EspressoIdling
import java.io.IOException
import javax.inject.Inject

class FavoriteServiceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
): IFavoriteService {
    override fun getFavoriteByType(type: String): PagingSource<Int, FavoriteEntity> = favoriteDao.getMovies(type)

    override fun isMovieAlreadyInFavorite(id: Int): Flow<Result<Boolean>> = flow {
        //EspressoIdling.increment()
        emit(Result.success(favoriteDao.countMoviesById(id) > 0))
        //if(!EspressoIdling.getEspressoIdlingResource.isIdleNow){ EspressoIdling.decrement() }
    }.catch {
        //if(!EspressoIdling.getEspressoIdlingResource.isIdleNow){ EspressoIdling.decrement() }
        emit(Result.failure(RuntimeException("Terdapat kesalahan, silahkan coba lagi")))
    }

    override suspend fun insertMovie(movie: FavoriteEntity): Flow<Result<String>> = flow {
        //EspressoIdling.increment()
        emit(Result.success("Loading..."))
        favoriteDao.insert(movie)
        //if(!EspressoIdling.getEspressoIdlingResource.isIdleNow){ EspressoIdling.decrement() }
        emit(Result.success("Data Berhasil Ditambahkan"))
    }.catch { e->
        //if(!EspressoIdling.getEspressoIdlingResource.isIdleNow){ EspressoIdling.decrement() }
        if(e is IOException){
            emit(Result.failure(RuntimeException("Oppss.. ada kesalahan saat menambahkan data")))
        }else{
            emit(Result.failure(RuntimeException("Terdapat kesalahan, silahkan coba lagi")))
        }
    }

    override suspend fun deleteMovie(movie: FavoriteEntity): Flow<Result<String>> = flow {
        //EspressoIdling.increment()
        emit(Result.success("Loading..."))
        favoriteDao.delete(movie)
        //if(!EspressoIdling.getEspressoIdlingResource.isIdleNow){ EspressoIdling.decrement() }
        emit(Result.success("Data Berhasil Dihapus"))
    }.catch { e->
        //if(!EspressoIdling.getEspressoIdlingResource.isIdleNow){ EspressoIdling.decrement() }
        if(e is IOException) {
            emit(Result.failure(RuntimeException("Oppss.. ada kesalahan saat menghapus data")))
        }else {
            emit(Result.failure(RuntimeException("Terdapat kesalahan, silahkan coba lagi")))
        }

    }

    override fun getMovieById(id: Int): Flow<Result<FavoriteEntity>> = flow {
        //EspressoIdling.increment()
        emit(Result.success(favoriteDao.getDetailById(id)))
        //if(!EspressoIdling.getEspressoIdlingResource.isIdleNow){ EspressoIdling.decrement() }
    }.catch {
        //if(!EspressoIdling.getEspressoIdlingResource.isIdleNow){ EspressoIdling.decrement() }
        emit(Result.failure(RuntimeException("Terdapat kesalahan, silahkan coba lagi")))
    }
}