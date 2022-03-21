package made.dicoding.moviecatalogueapps.core.data.repository

import androidx.paging.PagingSource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runBlockingTest
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper.MOVIES_TYPE
import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity
import made.dicoding.moviecatalogueapps.core.data.repository_impl.FavoriteRepositoryImpl
import made.dicoding.moviecatalogueapps.core.data.service.favorite.IFavoriteService
import made.dicoding.moviecatalogueapps.core.model.Movies
import made.dicoding.moviecatalogueapps.core.utils.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteRepositoryShould:BaseUnitTest() {

    private val service: IFavoriteService = mock()
    private val listMoviesSource:PagingSource<Int,FavoriteEntity> = mock()
    private val entity:FavoriteEntity = mock()
    private val payload:Movies = mock()

    private val msgLoading = "Loading..."
    private val msgSuccess = "Data Berhasil"
    private val catchException = "Terdapat kesalahan, silahkan coba lagi"
    private val catchIoException = "Oppss.. ada kesalahan saat menghapus data"

    private fun mockRepositorySuccessfulCase():FavoriteRepositoryImpl{
        runBlockingTest {
            whenever(service.getFavoriteByType(MOVIES_TYPE)).thenReturn(listMoviesSource)
            whenever(service.getMovieById(idMovieTesting)).thenReturn(flow {
                emit(Result.success(entity))
            })
            whenever(service.insertMovie(entity)).thenReturn(flow {
                emit(Result.success(msgLoading))
                emit(Result.success(msgSuccess))
            })
            whenever(service.deleteMovie(entity)).thenReturn(flow {
                emit(Result.success(msgLoading))
                emit(Result.success(msgSuccess))
            })
            whenever(payload.toFavoriteEntity()).thenReturn(entity)
            whenever(entity.toMovies()).thenReturn(payload)
        }
        return FavoriteRepositoryImpl(service)
    }

    @Test
    fun getFavoriteFromServiceThenSuccess() = runBlockingTest {
        val repo = mockRepositorySuccessfulCase()
        val data = repo.getFavoriteByType(MOVIES_TYPE)
        verify(service, times(1)).getFavoriteByType(MOVIES_TYPE)
        assertEquals(listMoviesSource,data)
    }

    @Test
    fun checkMovieAlreadyInFavoriteThenSuccess() = runBlockingTest {
        whenever(service.isMovieAlreadyInFavorite(idMovieTesting)).thenReturn(flow { emit(Result.success(true)) })
        val repo = mockRepositorySuccessfulCase()
        val data = repo.isMovieAlreadyInFavorite(idMovieTesting).first()
        verify(service, times(1)).isMovieAlreadyInFavorite(idMovieTesting)
        assertEquals(true,data.getOrNull())
    }

    @Test
    fun checkMovieNotReadyInFavoriteThenSuccess() = runBlockingTest {
        whenever(service.isMovieAlreadyInFavorite(idMovieTesting)).thenReturn(flow { emit(Result.success(false)) })
        val repo = mockRepositorySuccessfulCase()
        val data = repo.isMovieAlreadyInFavorite(idMovieTesting).first()
        verify(service, times(1)).isMovieAlreadyInFavorite(idMovieTesting)
        assertEquals(false,data.getOrNull())
    }

    @Test
    fun insertingMovieToService() = runBlockingTest {
        val repo = mockRepositorySuccessfulCase()
        val data = repo.insertMovie(payload)
        verify(service, times(1)).insertMovie(entity)
        assertEquals(msgLoading,data.first().getOrNull())
        assertEquals(msgSuccess,data.last().getOrNull())
    }

    @Test
    fun deletingMovieToService() = runBlockingTest {
        val repo = mockRepositorySuccessfulCase()
        val data = repo.deleteMovie(payload)
        verify(service, times(1)).deleteMovie(entity)
        assertEquals(msgLoading,data.first().getOrNull())
        assertEquals(msgSuccess,data.last().getOrNull())
    }

    @Test
    fun getFavoriteByIdFromService() = runBlockingTest {
        val repo = mockRepositorySuccessfulCase()
        val data = repo.favoriteMovieById(idMovieTesting).first()
        verify(service, times(1)).getMovieById(idMovieTesting)
        assertEquals(payload,data.getOrNull())
    }

    private fun mockRepositoryFailureCase():FavoriteRepositoryImpl{
        runBlockingTest {
            whenever(service.isMovieAlreadyInFavorite(idMovieTesting)).thenReturn(flow {
                emit(Result.failure(RuntimeException(catchException)))
            })
            whenever(service.insertMovie(entity)).thenReturn(flow {
                emit(Result.failure(RuntimeException(catchException)))
            })
            whenever(service.deleteMovie(entity)).thenReturn(flow {
                emit(Result.failure(RuntimeException(catchException)))
            })
            whenever(service.getMovieById(idMovieTesting)).thenReturn(flow {
                emit(Result.failure(RuntimeException(catchException)))
            })
            whenever(payload.toFavoriteEntity()).thenReturn(entity)
        }
        return FavoriteRepositoryImpl(service)
    }

    // failure test exception

    @Test
    fun checkMovieAlreadyInFavoriteThenFailure() = runBlockingTest {
        val repo = mockRepositoryFailureCase()
        val data = repo.isMovieAlreadyInFavorite(idMovieTesting).first()
        verify(service, times(1)).isMovieAlreadyInFavorite(idMovieTesting)
        assertEquals(catchException,data.exceptionOrNull()?.message)
    }

    @Test
    fun insertingMovieToServiceThenFailure() = runBlockingTest {
        val repo = mockRepositoryFailureCase()
        val data = repo.insertMovie(payload).first()
        verify(service, times(1)).insertMovie(entity)
        assertEquals(catchException,data.exceptionOrNull()?.message)
    }

    @Test
    fun deletingMovieToServiceThenFailure() = runBlockingTest {
        val repo = mockRepositoryFailureCase()
        val data = repo.deleteMovie(payload).first()
        verify(service, times(1)).deleteMovie(entity)
        assertEquals(catchException,data.exceptionOrNull()?.message)

    }

    @Test
    fun getFavoriteByIdFromServiceThenFailure() = runBlockingTest {
        val repo = mockRepositoryFailureCase()
        val data = repo.favoriteMovieById(idMovieTesting).first()
        verify(service, times(1)).getMovieById(idMovieTesting)
        assertEquals(catchException,data.exceptionOrNull()?.message)
    }

    private fun mockRepositoryFailureCaseIoException():FavoriteRepositoryImpl{
        runBlockingTest {

            whenever(service.insertMovie(entity)).thenReturn(flow {
                emit(Result.failure(RuntimeException(catchIoException)))
            })
            whenever(service.deleteMovie(entity)).thenReturn(flow {
                emit(Result.failure(RuntimeException(catchIoException)))
            })

            whenever(payload.toFavoriteEntity()).thenReturn(entity)
        }
        return FavoriteRepositoryImpl(service)
    }

    @Test
    fun insertingMovieToServiceThenFailure_IoException() = runBlockingTest {
        val repo = mockRepositoryFailureCaseIoException()
        val data = repo.insertMovie(payload).first()
        verify(service, times(1)).insertMovie(entity)
        assertEquals(catchIoException,data.exceptionOrNull()?.message)
    }

    @Test
    fun deletingMovieToServiceThenFailure_IoException() = runBlockingTest {
        val repo = mockRepositoryFailureCaseIoException()
        val data = repo.deleteMovie(payload).first()
        verify(service, times(1)).deleteMovie(entity)
        assertEquals(catchIoException,data.exceptionOrNull()?.message)

    }
}