package made.dicoding.moviecatalogueapps.favorite.presentation.favorite

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import made.dicoding.moviecatalogueapps.core.domain.repository.IFavoriteRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: IFavoriteRepository
):ViewModel() {

}