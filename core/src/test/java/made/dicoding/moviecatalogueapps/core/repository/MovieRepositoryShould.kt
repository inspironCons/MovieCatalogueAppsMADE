package made.dicoding.moviecatalogueapps.core.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import made.dicoding.moviecatalogueapps.core.data.repository_impl.MovieRepositoryImpl
import made.dicoding.moviecatalogueapps.core.service.detail_movie.IDetailMovieService
import made.dicoding.moviecatalogueapps.core.service.trending.ITrendingService
import made.dicoding.moviecatalogueapps.core.model.Movies
import made.dicoding.moviecatalogueapps.core.utils.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryShould:BaseUnitTest() {
    private val trendingService: ITrendingService = mock()
    private val detailService: IDetailMovieService = mock()

    private val listMovies:List<Movies> = mock()
    private val movie:Movies = mock()
    private val catchResultTrending= "Connection Lost, check your connection"
    private val catchResultDetail= "Something went wrong"
    private suspend fun mockMovieRepositorySuccessCase(): MovieRepositoryImpl {
        whenever(trendingService.fetchApiMovieList()).thenReturn(flow { emit(Result.success(listMovies)) })
        whenever(trendingService.fetchApiTvList()).thenReturn(flow { emit(Result.success(listMovies)) })
        whenever(detailService.fetchDetailMovies(idMovieTesting)).thenReturn(flow { emit(Result.success(movie)) })
        whenever(detailService.fetchDetailTV(idTvTesting)).thenReturn(flow { emit(Result.success(movie)) })
        return MovieRepositoryImpl(trendingService,detailService)
    }

    private suspend fun mockMovieRepositoryFailureCase(): MovieRepositoryImpl {
        whenever(trendingService.fetchApiMovieList()).thenReturn(flow { emit(Result.failure(RuntimeException(catchResultTrending))) })
        whenever(trendingService.fetchApiTvList()).thenReturn(flow { emit(Result.failure(RuntimeException(catchResultTrending))) })
        whenever(detailService.fetchDetailMovies(idMovieTesting)).thenReturn(flow { emit(Result.failure(RuntimeException(catchResultDetail))) })
        whenever(detailService.fetchDetailTV(idTvTesting)).thenReturn(flow { emit(Result.failure(RuntimeException(catchResultDetail))) })
        return MovieRepositoryImpl(trendingService,detailService)
    }

    @Test
    fun getTrendingListMoviesFromTrendingServiceThenSuccess() = runBlockingTest {
        val repo = mockMovieRepositorySuccessCase()
        val data = repo.trendingMovies().first()
        verify(trendingService, times(1)).fetchApiMovieList()
        assertEquals(listMovies,data.getOrNull())
    }

    @Test
    fun getTrendingListTvShowsFromTrendingServiceThenSuccess() = runBlockingTest {
        val repo = mockMovieRepositorySuccessCase()
        val data = repo.trendingTvShows().first()
        verify(trendingService, times(1)).fetchApiTvList()
        assertEquals(listMovies,data.getOrNull())
    }

    @Test
    fun getDetailMovieFromDetailServiceThenSuccess() = runBlockingTest {
        val repo = mockMovieRepositorySuccessCase()
        val data = repo.detailMovie(idMovieTesting).first()
        verify(detailService, times(1)).fetchDetailMovies(idMovieTesting)
        assertEquals(movie,data.getOrNull())
    }

    @Test
    fun getDetailTvShowsFromDetailServiceThenSuccess() = runBlockingTest {
        val repo = mockMovieRepositorySuccessCase()
        val data = repo.detailTvShows(idTvTesting).first()
        verify(detailService, times(1)).fetchDetailTV(idTvTesting)
        assertEquals(movie,data.getOrNull())
    }

    //failure case
    @Test
    fun getTrendingListMoviesFromTrendingServiceThenFailure() = runBlockingTest {
        val repo = mockMovieRepositoryFailureCase()
        val data = repo.trendingMovies().first()
        verify(trendingService, times(1)).fetchApiMovieList()
        assertEquals(catchResultTrending,data.exceptionOrNull()?.message)
    }

    @Test
    fun getTrendingListTvShowsFromTrendingServiceThenFailure() = runBlockingTest {
        val repo = mockMovieRepositoryFailureCase()
        val data = repo.trendingTvShows().first()
        verify(trendingService, times(1)).fetchApiTvList()
        assertEquals(catchResultTrending,data.exceptionOrNull()?.message)
    }

    @Test
    fun getDetailMovieFromDetailServiceThenFailure() = runBlockingTest {
        val repo = mockMovieRepositoryFailureCase()
        val data = repo.detailMovie(idMovieTesting).first()
        verify(detailService, times(1)).fetchDetailMovies(idMovieTesting)
        assertEquals(catchResultDetail,data.exceptionOrNull()?.message)
    }

    @Test
    fun getDetailTvShowsFromDetailServiceThenFailure() = runBlockingTest {
        val repo = mockMovieRepositoryFailureCase()
        val data = repo.detailTvShows(idTvTesting).first()
        verify(detailService, times(1)).fetchDetailTV(idTvTesting)
        assertEquals(catchResultDetail,data.exceptionOrNull()?.message)
    }
}