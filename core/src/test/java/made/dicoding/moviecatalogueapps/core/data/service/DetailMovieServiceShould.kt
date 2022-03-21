package made.dicoding.moviecatalogueapps.core.data.service

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper
import made.dicoding.moviecatalogueapps.core.data.remote.network.DetailMoviesApi
import made.dicoding.moviecatalogueapps.core.data.remote.network.dto.DetailMoviesResponse
import made.dicoding.moviecatalogueapps.core.data.remote.network.dto.DetailTvShowsResponse
import made.dicoding.moviecatalogueapps.core.data.service.detail_movie.DetailMovieServiceImpl
import made.dicoding.moviecatalogueapps.core.model.Movies
import made.dicoding.moviecatalogueapps.core.utils.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailMovieServiceShould:BaseUnitTest() {
    private val api: DetailMoviesApi = mock()
    private val detailResponseMovieMock = mock<DetailMoviesResponse>()
    private val detailResponseTvMock = mock<DetailTvShowsResponse>()
    private val movieMock = mock<Movies>()

    private val errorMsg = "Something went wrong"
    private suspend fun mocDetailMovieServiceSuccessfulCase():DetailMovieServiceImpl{
        whenever(api.getDetailMovie(
            idMovieTesting,
            ConstanNameHelper.API_KEY,
            ConstanNameHelper.LANGUAGE
        )).thenReturn(detailResponseMovieMock)
        whenever(api.getDetailTv(
            idTvTesting,
            ConstanNameHelper.API_KEY,
            ConstanNameHelper.LANGUAGE
        )).thenReturn(detailResponseTvMock)
        whenever(detailResponseMovieMock.toMovies()).thenReturn(movieMock)
        whenever(detailResponseTvMock.toMovies()).thenReturn(movieMock)
        return DetailMovieServiceImpl(api)
    }

    private suspend fun mockServiceDetailMovieFailureCase():DetailMovieServiceImpl{
        whenever(api.getDetailMovie(
            idMovieTesting,
            ConstanNameHelper.API_KEY,
            ConstanNameHelper.LANGUAGE
        )).thenThrow(RuntimeException(errorMsg))

        whenever(api.getDetailTv(
            idTvTesting,
            ConstanNameHelper.API_KEY,
            ConstanNameHelper.LANGUAGE
        )).thenThrow(RuntimeException(errorMsg))

        return DetailMovieServiceImpl(api)
    }
    

    @Test
    fun getDetailMoviesFromApiService() = runBlockingTest {
        val service = mocDetailMovieServiceSuccessfulCase()
        val request = service.fetchDetailMovies(idMovieTesting).first()
        verify(api, times(1)).getDetailMovie(
            idMovieTesting,
            ConstanNameHelper.API_KEY,
            ConstanNameHelper.LANGUAGE
        )

        assertEquals(movieMock,request.getOrNull())
    }

    @Test
    fun getDetailMoviesFromApiServiceThenNetworkFails() = runBlockingTest {
        val service = mockServiceDetailMovieFailureCase()
        val request = service.fetchDetailMovies(idMovieTesting).first()
        verify(api, times(1)).getDetailMovie(
            idMovieTesting,
            ConstanNameHelper.API_KEY,
            ConstanNameHelper.LANGUAGE
        )

        assertEquals(errorMsg,request.exceptionOrNull()?.message)
    }


    @Test
    fun getDetailTvShowsFromApiService() = runBlockingTest {
        val service = mocDetailMovieServiceSuccessfulCase()
        val request = service.fetchDetailTV(idTvTesting).first()
        verify(api, times(1)).getDetailTv(
            idTvTesting,
            ConstanNameHelper.API_KEY,
            ConstanNameHelper.LANGUAGE
        )

        assertEquals(movieMock,request.getOrNull())
    }

    @Test
    fun getDetailTvShowsFromApiServiceThenNetworkFails() = runBlockingTest {
        val service = mockServiceDetailMovieFailureCase()
        val request = service.fetchDetailTV(idTvTesting).first()
        verify(api, times(1)).getDetailTv(
            idTvTesting,
            ConstanNameHelper.API_KEY,
            ConstanNameHelper.LANGUAGE
        )
        assertEquals(errorMsg,request.exceptionOrNull()?.message)
    }
}