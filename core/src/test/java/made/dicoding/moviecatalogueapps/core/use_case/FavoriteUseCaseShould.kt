package made.dicoding.moviecatalogueapps.core.use_case

import androidx.paging.PagingSource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper.MOVIES_TYPE
import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity
import made.dicoding.moviecatalogueapps.core.domain.repository.IFavoriteRepository
import made.dicoding.moviecatalogueapps.core.domain.use_case.favorite.FavoriteUseCaseImpl
import made.dicoding.moviecatalogueapps.core.utils.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteUseCaseShould:BaseUnitTest() {
    private val iFavoriteRepository: IFavoriteRepository = mock()
    private val pagingSourceMovie:PagingSource<Int,FavoriteEntity> = mock()

    @Test
    fun getPagingSourceByTypeFromRepo() = runBlockingTest {
        whenever(iFavoriteRepository.getFavoriteByType(MOVIES_TYPE)).thenReturn(pagingSourceMovie)
        val useCase = FavoriteUseCaseImpl(iFavoriteRepository)
        val paging = useCase.getMovieByType(MOVIES_TYPE)
        verify(iFavoriteRepository, times(1)).getFavoriteByType(MOVIES_TYPE)
        assertEquals(pagingSourceMovie,paging)
    }
}