package made.dicoding.moviecatalogueapps.core.use_case

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runBlockingTest
import made.dicoding.moviecatalogueapps.core.domain.repository.IFavoriteRepository
import made.dicoding.moviecatalogueapps.core.domain.repository.IMovieRepository
import made.dicoding.moviecatalogueapps.core.domain.use_case.movie.MovieUseCaseImpl
import made.dicoding.moviecatalogueapps.core.model.DetailMovie
import made.dicoding.moviecatalogueapps.core.model.Movies
import made.dicoding.moviecatalogueapps.core.utils.BaseUnitTest
import made.dicoding.moviecatalogueapps.core.utils.mapper.MovieMapper
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieUseCaseShould:BaseUnitTest() {
    private val movieRepo: IMovieRepository = mock()
    private val favoriteRepo: IFavoriteRepository = mock()
    private val mapper:MovieMapper = mock()

    private val movieData:Movies = mock()
    private val movieDetailData:DetailMovie = mock()

    private val msgLoading = "Loading..."
    private val msgSuccess = "Data Berhasil"
    private val catchException = "Terdapat kesalahan, silahkan coba lagi"
    private val catchIoException = "Oppss.. ada kesalahan saat menghapus data"

    private fun useCaseSuccessfulCase():MovieUseCaseImpl{
        runBlockingTest {
            whenever(mapper.invoke(movieData)).thenReturn(movieDetailData)
            whenever(movieDetailData.toMovie()).thenReturn(movieData)

            whenever(movieRepo.detailMovie(idMovieTesting)).thenReturn(flow {
                emit(Result.success(movieData))
            })
            whenever(movieRepo.detailTvShows(idTvTesting)).thenReturn(flow {
                emit(Result.success(movieData))
            })
            whenever(favoriteRepo.deleteMovie(movieData)).thenReturn(flow {
                emit(Result.success(msgLoading))
                emit(Result.success(msgSuccess))
            })
            whenever(favoriteRepo.insertMovie(movieData)).thenReturn(flow {
                emit(Result.success(msgLoading))
                emit(Result.success(msgSuccess))
            })
        }
        return MovieUseCaseImpl(movieRepo,favoriteRepo,mapper)
    }

    @Test
    fun getDetailMovieFromMovieRepoThenSuccess() = runBlockingTest {
        val useCase = useCaseSuccessfulCase()
        val detailMovie = useCase.getDetailMovie(idMovieTesting).first()
        verify(movieRepo, times(1)).detailMovie(idMovieTesting)
        assertEquals(movieDetailData,detailMovie.getOrNull())
    }

    @Test
    fun getDetailTvShowFromMovieRepoThenSuccess() = runBlockingTest {
        val useCase = useCaseSuccessfulCase()
        val detailTv = useCase.getDetailTvShow(idTvTesting).first()
        verify(movieRepo, times(1)).detailTvShows(idTvTesting)
        assertEquals(movieDetailData,detailTv.getOrNull())
    }

    @Test
    fun checkMovieAlreadyInFavoriteThenSuccess() = runBlockingTest {
        whenever(favoriteRepo.isMovieAlreadyInFavorite(idMovieTesting)).thenReturn(flow {
            emit(Result.success(true))
        })
        val useCase = useCaseSuccessfulCase()
        val isTrue = useCase.isMovieAlreadyInFavorite(idMovieTesting).last()
        verify(favoriteRepo, times(1)).isMovieAlreadyInFavorite(idMovieTesting)
        assertEquals(true,isTrue.getOrNull())
    }
    @Test
    fun checkMovieNotReadyInFavoriteThenSuccess() = runBlockingTest {
        whenever(favoriteRepo.isMovieAlreadyInFavorite(idMovieTesting)).thenReturn(flow {
            emit(Result.success(false))
        })
        val useCase = useCaseSuccessfulCase()
        val isTrue = useCase.isMovieAlreadyInFavorite(idMovieTesting).last()
        verify(favoriteRepo, times(1)).isMovieAlreadyInFavorite(idMovieTesting)
        assertEquals(false,isTrue.getOrNull())
    }

    @Test
    fun insertDataMovieLocalToRepoFunction() = runBlockingTest {
        val useCase = useCaseSuccessfulCase()
        val data = useCase.insertMovieToFavorite(movieDetailData)
        verify(favoriteRepo, times(1)).insertMovie(movieData)
        assertEquals(msgLoading,data.first().getOrNull())
        assertEquals(msgSuccess,data.last().getOrNull())
    }

    @Test
    fun deleteDataMovieLocalToRepoFunction() = runBlockingTest {
        val useCase = useCaseSuccessfulCase()
        val data = useCase.deleteMovieFromFavorite(movieDetailData)
        verify(favoriteRepo, times(1)).deleteMovie(movieData)
        assertEquals(msgLoading,data.first().getOrNull())
        assertEquals(msgSuccess,data.last().getOrNull())
    }

    //failure case exception
    private fun useCaseFailureCase():MovieUseCaseImpl {
        runBlockingTest {
            whenever(movieDetailData.toMovie()).thenReturn(movieData)
            whenever(movieRepo.detailMovie(idMovieTesting)).thenReturn(flow {
                emit(Result.failure(RuntimeException(catchException)))
            })
            whenever(favoriteRepo.isMovieAlreadyInFavorite(idMovieTesting)).thenReturn(flow {
                emit(Result.failure(RuntimeException(catchException)))
            })
            whenever(movieRepo.detailTvShows(idTvTesting)).thenReturn(flow {
                emit(Result.failure(RuntimeException(catchException)))
            })
            whenever(favoriteRepo.deleteMovie(movieData)).thenReturn(flow {
                emit(Result.failure(RuntimeException(catchException)))
            })
            whenever(favoriteRepo.insertMovie(movieData)).thenReturn(flow {
                emit(Result.failure(RuntimeException(catchException)))
            })
        }
        return MovieUseCaseImpl(movieRepo,favoriteRepo,mapper)
    }

    @Test
    fun getDetailMovieFromMovieRepoThenFailure() = runBlockingTest {
        val useCase = useCaseFailureCase()
        val detailMovie = useCase.getDetailMovie(idMovieTesting).first()
        verify(movieRepo, times(1)).detailMovie(idMovieTesting)
        assertEquals(catchException,detailMovie.exceptionOrNull()?.message)
    }

    @Test
    fun getDetailTvShowFromMovieRepoThenFailure() = runBlockingTest {
        val useCase = useCaseFailureCase()
        val detailTv = useCase.getDetailTvShow(idTvTesting).first()
        verify(movieRepo, times(1)).detailTvShows(idTvTesting)
        assertEquals(catchException,detailTv.exceptionOrNull()?.message)
    }

    @Test
    fun checkMovieAreAlreadyInFavoriteThenFailure() = runBlockingTest {

        val useCase = useCaseFailureCase()
        val isTrue = useCase.isMovieAlreadyInFavorite(idMovieTesting).first()
        verify(favoriteRepo, times(1)).isMovieAlreadyInFavorite(idMovieTesting)
        assertEquals(catchException,isTrue.exceptionOrNull()?.message)
    }

    @Test
    fun insertDataMovieLocalToRepoFunctionThenFailure() = runBlockingTest {
        val useCase = useCaseFailureCase()
        val data = useCase.insertMovieToFavorite(movieDetailData)
        verify(favoriteRepo, times(1)).insertMovie(movieData)
        assertEquals(catchException,data.first().exceptionOrNull()?.message)
    }

    @Test
    fun deleteDataMovieLocalToRepoFunctionThenFailure() = runBlockingTest {
        val useCase = useCaseFailureCase()
        val data = useCase.deleteMovieFromFavorite(movieDetailData)
        verify(favoriteRepo, times(1)).deleteMovie(movieData)
        assertEquals(catchException,data.first().exceptionOrNull()?.message)
    }

    //failure case exception
    private fun useCaseIoFailureCase():MovieUseCaseImpl {
        runBlockingTest {
            whenever(movieDetailData.toMovie()).thenReturn(movieData)
            whenever(favoriteRepo.deleteMovie(movieData)).thenReturn(flow {
                emit(Result.failure(RuntimeException(catchIoException)))
            })
            whenever(favoriteRepo.insertMovie(movieData)).thenReturn(flow {
                emit(Result.failure(RuntimeException(catchIoException)))
            })
        }
        return MovieUseCaseImpl(movieRepo,favoriteRepo,mapper)
    }

    @Test
    fun insertDataMovieLocalToRepoFunctionThenFailure_IOException() = runBlockingTest {
        val useCase = useCaseIoFailureCase()
        val data = useCase.insertMovieToFavorite(movieDetailData)
        verify(favoriteRepo, times(1)).insertMovie(movieData)
        assertEquals(catchIoException,data.first().exceptionOrNull()?.message)
    }

    @Test
    fun deleteDataMovieLocalToRepoFunctionThenFailure_IOException() = runBlockingTest {
        val useCase = useCaseIoFailureCase()
        val data = useCase.deleteMovieFromFavorite(movieDetailData)
        verify(favoriteRepo, times(1)).deleteMovie(movieData)
        assertEquals(catchIoException,data.first().exceptionOrNull()?.message)
    }
}