package made.dicoding.moviecatalogueapps.favorite.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.map
import made.dicoding.moviecatalogueapps.core.domain.use_case.favorite.IFavoriteUseCase

class FavoriteViewModel(
    private val useCase: IFavoriteUseCase
):ViewModel() {
    fun getMovieByType(type:String) = Pager(
        config = PagingConfig(pageSize = 5, enablePlaceholders = true, maxSize = 200)
    ){
        useCase.getMovieByType(type)
    }.flow.map { source->
        source.map { favorite -> favorite.toMovies() }
    }.cachedIn(viewModelScope)
}