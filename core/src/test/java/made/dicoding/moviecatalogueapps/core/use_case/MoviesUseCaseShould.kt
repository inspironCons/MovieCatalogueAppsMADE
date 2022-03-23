package made.dicoding.moviecatalogueapps.core.use_case

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import made.dicoding.moviecatalogueapps.core.domain.repository.IMovieRepository
import made.dicoding.moviecatalogueapps.core.domain.use_case.movies.MoviesUseCaseImpl
import made.dicoding.moviecatalogueapps.core.model.ListMovies
import made.dicoding.moviecatalogueapps.core.model.Movies
import made.dicoding.moviecatalogueapps.core.utils.BaseUnitTest
import made.dicoding.moviecatalogueapps.core.utils.mapper.ListMoviesMapper
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesUseCaseShould:BaseUnitTest() {
    private val repository: IMovieRepository = mock()
    private val listMovieMapper:ListMoviesMapper = mock()
    private val listMovieRepo: List<Movies> = mock()
    private val listMovieUseCase: List<ListMovies> = mock()

    private val errMsg = "Terjadi Kesalahan"

    private fun useCaseSuccessfulCase():MoviesUseCaseImpl{
        runBlockingTest {
            whenever(repository.trendingMovies()).thenReturn(flow { emit(Result.success(listMovieRepo)) })
            whenever(repository.trendingTvShows()).thenReturn(flow { emit(Result.success(listMovieRepo)) })
            whenever(listMovieMapper.invoke(listMovieRepo)).thenReturn(listMovieUseCase)
        }

        return MoviesUseCaseImpl(repository,listMovieMapper)
    }

    @Test
    fun getMovieListFromRepoThenSuccess() = runBlockingTest {
        val useCase = useCaseSuccessfulCase()
        val data = useCase.getMovieList().first()
        verify(repository, times(1)).trendingMovies()
        assertEquals(listMovieUseCase,data.getOrNull())
    }

    @Test
    fun getTvShowListFromRepoThenSuccess() = runBlockingTest {
        val useCase = useCaseSuccessfulCase()
        val data = useCase.getTvShowList().first()
        verify(repository, times(1)).trendingTvShows()
        assertEquals(listMovieUseCase,data.getOrNull())
    }

    //failure

    private fun useCaseFailureCase():MoviesUseCaseImpl{
        runBlockingTest {
            whenever(repository.trendingMovies()).thenReturn(flow { emit(Result.failure(RuntimeException(errMsg))) })
            whenever(repository.trendingTvShows()).thenReturn(flow { emit(Result.failure(RuntimeException(errMsg))) })
        }

        return MoviesUseCaseImpl(repository,listMovieMapper)
    }

    @Test
    fun getMovieListFromRepoThenFailure() = runBlockingTest {
        val useCase = useCaseFailureCase()
        val data = useCase.getMovieList().first()
        verify(repository, times(1)).trendingMovies()
        assertEquals(errMsg,data.exceptionOrNull()?.message)
    }

    @Test
    fun getTvShowListFromRepoThenFailure() = runBlockingTest {
        val useCase = useCaseFailureCase()
        val data = useCase.getTvShowList().first()
        verify(repository, times(1)).trendingTvShows()
        assertEquals(errMsg,data.exceptionOrNull()?.message)
    }

}