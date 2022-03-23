package made.dicoding.moviecatalogueapps.favorite

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingSource
import androidx.recyclerview.widget.ListUpdateCallback
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper.MOVIES_TYPE
import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity
import made.dicoding.moviecatalogueapps.core.domain.use_case.favorite.IFavoriteUseCase
import made.dicoding.moviecatalogueapps.core.model.Movies
import made.dicoding.moviecatalogueapps.core.presentation.FavoriteAdapter
import made.dicoding.moviecatalogueapps.core.utils.BaseUnitTest
import made.dicoding.moviecatalogueapps.favorite.presentation.favorite.FavoriteViewModel
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteViewModelShould:BaseUnitTest() {
    private val useCase: IFavoriteUseCase = mock()
    private val listMovie = mock<List<Movies>>()
    private val pageSourceMock:PagingSource<Int, FavoriteEntity> = mock()

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
    private val tesDispatcher = TestCoroutineDispatcher()
    @Before
    fun setup(){
        Dispatchers.setMain(tesDispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    private fun viewModelSuccessfulCase(): FavoriteViewModel {
        whenever(useCase.getMovieByType(MOVIES_TYPE)).thenReturn(pageSourceMock)
        return FavoriteViewModel(useCase)
    }

    @Test
    fun getListMovie() = runBlockingTest {
        val viewModel = viewModelSuccessfulCase()

        val differ = AsyncPagingDataDiffer(
            diffCallback = FavoriteAdapter.diffCallback,
            mainDispatcher = tesDispatcher,
            workerDispatcher = tesDispatcher,
            updateCallback = noopListUpdateCallback
        )

        val job = launch {
            viewModel.getMovieByType(MOVIES_TYPE).collectLatest {
                differ.submitData(it)
            }
        }

        advanceUntilIdle()
        verify(useCase, times(1)).getMovieByType(MOVIES_TYPE)
        Assert.assertEquals(differ.snapshot(),listMovie)
        job.cancel()
    }
}