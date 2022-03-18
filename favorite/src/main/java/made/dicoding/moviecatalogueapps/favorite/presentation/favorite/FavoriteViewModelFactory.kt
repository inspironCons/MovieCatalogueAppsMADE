package made.dicoding.moviecatalogueapps.favorite.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import made.dicoding.moviecatalogueapps.core.domain.use_case.favorite.IFavoriteUseCase
import javax.inject.Inject

class FavoriteViewModelFactory @Inject constructor(
    private val useCase: IFavoriteUseCase
):ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(FavoriteViewModel::class.java)->{
                FavoriteViewModel(useCase) as T
            }
            else->{
                throw Throwable("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }
}