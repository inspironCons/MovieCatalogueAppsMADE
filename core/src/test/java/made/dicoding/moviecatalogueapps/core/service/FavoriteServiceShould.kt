package made.dicoding.moviecatalogueapps.core.service

import androidx.paging.PagingSource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper.MOVIES_TYPE
import made.dicoding.moviecatalogueapps.core.data.remote.local.dao.FavoriteDao
import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity
import made.dicoding.moviecatalogueapps.core.service.favorite.FavoriteServiceImpl
import made.dicoding.moviecatalogueapps.core.utils.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteServiceShould:BaseUnitTest() {
    private val favoriteDao:FavoriteDao = mock()
    private val pagingSource:PagingSource<Int,FavoriteEntity> = mock()
    private val entityMock:FavoriteEntity = mock()
    private val errorMsg = "Terdapat kesalahan, silahkan coba lagi"

    private val service = FavoriteServiceImpl(favoriteDao)

    @Test
    fun getFavoriteByTypeThenSuccessFromDao() = runBlockingTest {
        whenever(favoriteDao.getMovies(MOVIES_TYPE)).thenReturn(
            pagingSource
        )

        val data = service.getFavoriteByType(MOVIES_TYPE)

        verify(favoriteDao, times(1)).getMovies(MOVIES_TYPE)
        assertEquals(pagingSource, data)
    }

    @Test
    fun checkDataMovieIsAlreadyFromDaoThenSuccess() = runBlockingTest {
        whenever(favoriteDao.countMoviesById(idMovieTesting)).thenReturn(1)
        val count = service.isMovieAlreadyInFavorite(idMovieTesting).first()
        verify(favoriteDao, times(1)).countMoviesById(idMovieTesting)
        assertEquals(true, count.getOrNull())
    }

    @Test
    fun checkDataMovieNotAlreadyFromDaoThenSuccess() = runBlockingTest {
        whenever(favoriteDao.countMoviesById(idMovieTesting)).thenReturn(0)
        val count = service.isMovieAlreadyInFavorite(idMovieTesting).first()
        verify(favoriteDao, times(1)).countMoviesById(idMovieTesting)
        assertEquals(false, count.getOrNull())
    }

    @Test
    fun checkDataMovieIsAlreadyFromDaoThenFailure() = runBlockingTest {
        whenever(favoriteDao.countMoviesById(idMovieTesting)).thenThrow(
            RuntimeException(errorMsg)
        )
        val count = service.isMovieAlreadyInFavorite(idMovieTesting).first()
        verify(favoriteDao, times(1)).countMoviesById(idMovieTesting)
        assertEquals(errorMsg, count.exceptionOrNull()?.message)

    }

    @Test
    fun checkDataMovieNotAlreadyFromDaoThenFailure() = runBlockingTest {
        whenever(favoriteDao.countMoviesById(idMovieTesting)).thenThrow(
            RuntimeException(errorMsg)
        )
        val count = service.isMovieAlreadyInFavorite(idMovieTesting).first()
        verify(favoriteDao, times(1)).countMoviesById(idMovieTesting)
        assertEquals(errorMsg, count.exceptionOrNull()?.message)
    }

    @Test
    fun checkingInsertMovie() = runBlockingTest {
        service.insertMovie(entityMock).collect()
        verify(favoriteDao, times(1)).insert(entityMock)
    }

    @Test
    fun checkingDeletedMovie() = runBlockingTest {
        service.deleteMovie(entityMock).collect()
        verify(favoriteDao, times(1)).delete(entityMock)
    }

    @Test
    fun getMovieByIdFromDaoThenSuccess() = runBlockingTest {
        whenever(favoriteDao.getDetailById(idMovieTesting)).thenReturn(entityMock)
        val data = service.getMovieById(idMovieTesting).first()
        verify(favoriteDao, times(1)).getDetailById(idMovieTesting)
        assertEquals(entityMock,data.getOrNull())


    }
}