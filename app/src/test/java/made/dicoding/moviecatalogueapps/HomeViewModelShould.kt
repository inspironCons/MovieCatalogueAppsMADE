package made.dicoding.moviecatalogueapps

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import made.dicoding.moviecatalogueapps.core.domain.use_case.movies.IMoviesUseCase
import made.dicoding.moviecatalogueapps.core.model.ListMovies
import made.dicoding.moviecatalogueapps.core.utils.BaseUnitTest
import made.dicoding.moviecatalogueapps.core.utils.captureValues
import made.dicoding.moviecatalogueapps.core.utils.getValueForTest
import made.dicoding.moviecatalogueapps.presentation.home.HomeViewModel
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class HomeViewModelShould: BaseUnitTest() {
    private val useCase: IMoviesUseCase = mock()
    private val listMovieUseCase:List<ListMovies> = mock()
    private val msgError = "Lost Connection"
    private fun viewModelSuccessfulCase():HomeViewModel{
        runBlockingTest {
            whenever(useCase.getMovieList()).thenReturn(flow {
                emit(Result.success(listMovieUseCase))
            })
            whenever(useCase.getTvShowList()).thenReturn(flow {
                emit(Result.success(listMovieUseCase))
            })
        }
        return HomeViewModel(useCase)
    }

    @Test
    fun getMovieListFromUseCase() = runBlockingTest {
        val viewModel = viewModelSuccessfulCase()
        viewModel.getMovieList()
        verify(useCase, times(1)).getMovieList()
        val list = viewModel.getMovies().getValueForTest()
        assertEquals(listMovieUseCase,list)
    }

    @Test
    fun getTvShowsListFromUseCase() = runBlockingTest {
        val viewModel = viewModelSuccessfulCase()
        viewModel.getTvShowList()
        verify(useCase, times(1)).getTvShowList()
        val list = viewModel.getTvShows().getValueForTest()
        assertEquals(listMovieUseCase,list)
    }

    @Test
    fun showLoaderWhenLoadMovieList() = runBlockingTest{
        val vieModel = viewModelSuccessfulCase()
        vieModel.getLoaderMovie().captureValues {
            vieModel.getMovieList()
            assertEquals(true,values.first())
        }
    }
    @Test
    fun hideLoaderWhenLoadMovieList() = runBlockingTest{
        val vieModel = viewModelSuccessfulCase()
        vieModel.getLoaderMovie().captureValues {
            vieModel.getMovieList()
            assertEquals(false,values.last())
        }
    }

    @Test
    fun showLoaderWhenLoadTvShowsList() = runBlockingTest{
        val vieModel = viewModelSuccessfulCase()
        vieModel.getLoaderTvShows().captureValues {
            vieModel.getTvShowList()
            assertEquals(true,values.first())
        }
    }
    @Test
    fun hideLoaderWhenLoadTvShowsList() = runBlockingTest{
        val vieModel = viewModelSuccessfulCase()
        vieModel.getLoaderTvShows().captureValues {
            vieModel.getTvShowList()
            assertEquals(false,values.last())
        }
    }

    //failure case
    private fun viewModelFailureCase():HomeViewModel{
        runBlockingTest {
            whenever(useCase.getMovieList()).thenReturn(flow {
                emit(Result.failure(RuntimeException(msgError)))
            })
            whenever(useCase.getTvShowList()).thenReturn(flow {
                emit(Result.failure(RuntimeException(msgError)))
            })
        }
        return HomeViewModel(useCase)
    }

    @Test
    fun getMovieListFromUseCaseThenFailure() = runBlockingTest {
        val viewModel = viewModelFailureCase()
        viewModel.getMovieList()
        verify(useCase, times(1)).getMovieList()
        viewModel.errorDataMovies().captureValues {
            assertEquals(Pair(true,msgError),values.first())
        }
    }

    @Test
    fun getTvShowsListFromUseCaseThenFailure() = runBlockingTest {
        val viewModel = viewModelFailureCase()
        viewModel.getTvShowList()
        verify(useCase, times(1)).getTvShowList()
        viewModel.errorDataTvShows().captureValues {
            assertEquals(Pair(true,msgError),values.first())
        }
    }
}