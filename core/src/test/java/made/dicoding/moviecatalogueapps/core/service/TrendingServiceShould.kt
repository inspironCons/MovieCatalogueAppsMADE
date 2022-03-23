package made.dicoding.moviecatalogueapps.core.service

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper
import made.dicoding.moviecatalogueapps.core.data.remote.network.TrendingApi
import made.dicoding.moviecatalogueapps.core.data.remote.network.dto.TrendingMovieResponse
import made.dicoding.moviecatalogueapps.core.data.remote.network.dto.TrendingTvResponse
import made.dicoding.moviecatalogueapps.core.service.trending.TrendingServiceImpl
import made.dicoding.moviecatalogueapps.core.utils.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class TrendingServiceShould:BaseUnitTest() {
    private val api: TrendingApi = mock()
    private val movieFromNetworkResult = mock<TrendingMovieResponse>()
    private val tvShowsFromNetworkResult = mock<TrendingTvResponse>()
    private val catchResult= "Connection Lost, check your connection"

    private suspend fun mockServiceSuccessCase():TrendingServiceImpl{
        whenever(api.getTrendingMovieOnThisWeek(
            ConstanNameHelper.API_KEY,
            ConstanNameHelper.LANGUAGE,
            ConstanNameHelper.REGIONS
        )).thenReturn(movieFromNetworkResult)

        whenever(api.getTrendingTvShowsOnThisWeek(
            ConstanNameHelper.API_KEY,
            ConstanNameHelper.LANGUAGE,
            ConstanNameHelper.REGIONS
        )).thenReturn(tvShowsFromNetworkResult)

        return TrendingServiceImpl(api)
    }

    @Test
    fun getMovieFromApiThenSuccess() = runBlockingTest {
        val service = mockServiceSuccessCase()
        val data = service.fetchApiMovieList().first()
        verify(api, times(1)).getTrendingMovieOnThisWeek(
            ConstanNameHelper.API_KEY,
            ConstanNameHelper.LANGUAGE,
            ConstanNameHelper.REGIONS)

        assertEquals(movieFromNetworkResult.results,data.getOrNull())
    }

    private suspend fun mockServiceFailureCase():TrendingServiceImpl{
        whenever(api.getTrendingMovieOnThisWeek(
            ConstanNameHelper.API_KEY,
            ConstanNameHelper.LANGUAGE,
            ConstanNameHelper.REGIONS
        )).thenThrow(RuntimeException(catchResult))

        whenever(api.getTrendingTvShowsOnThisWeek(
            ConstanNameHelper.API_KEY,
            ConstanNameHelper.LANGUAGE,
            ConstanNameHelper.REGIONS
        )).thenThrow(RuntimeException(catchResult))

        return TrendingServiceImpl(api)
    }

    @Test
    fun getMovieFromApiThenNetworkFail() = runBlockingTest {
        val service = mockServiceFailureCase()
        val data = service.fetchApiMovieList().first()
        verify(api, times(1)).getTrendingMovieOnThisWeek(
            ConstanNameHelper.API_KEY,
            ConstanNameHelper.LANGUAGE,
            ConstanNameHelper.REGIONS)

        assertEquals(catchResult,data.exceptionOrNull()?.message)
    }

    @Test
    fun getTvShowsFromApiThenSuccess() = runBlockingTest {
        val service = mockServiceSuccessCase()
        val data = service.fetchApiTvList().first()
        verify(api, times(1)).getTrendingTvShowsOnThisWeek(
            ConstanNameHelper.API_KEY,
            ConstanNameHelper.LANGUAGE,
            ConstanNameHelper.REGIONS)

        assertEquals(movieFromNetworkResult.results,data.getOrNull())
    }

    @Test
    fun getTvShowsFromApiThenNetworkFail() = runBlockingTest {
        val service = mockServiceFailureCase()
        val data = service.fetchApiTvList().first()
        verify(api, times(1)).getTrendingTvShowsOnThisWeek(
            ConstanNameHelper.API_KEY,
            ConstanNameHelper.LANGUAGE,
            ConstanNameHelper.REGIONS)

        assertEquals(catchResult,data.exceptionOrNull()?.message)
    }
}