package made.dicoding.moviecatalogueapps.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper.MOVIES_TYPE
import made.dicoding.moviecatalogueapps.core.domain.use_case.movie.IMovieUseCase
import made.dicoding.moviecatalogueapps.core.model.DetailMovie
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val useCase: IMovieUseCase
): ViewModel() {
    var movies: DetailMovie = DetailMovie()
//    var isMovieFavorite = MutableLiveData<Boolean>()

    fun setDetailMovie(data: DetailMovie?, type:String){
       if(data != null){
           movies = data
           movies.type = type
       }
    }
    fun detailMovie(id:Int,type:String,getSourceLocal:Boolean) =  liveData {
        if(!getSourceLocal) {
            when (type) {
                MOVIES_TYPE -> emitSource(useCase.getDetailMovie(id).asLiveData())
                else -> emitSource(useCase.getDetailTvShow(id).asLiveData())
            }
        }
    }

//    fun checkFavorite() = viewModelScope.launch {
//        favoriteRepo.isMoviesInTheFavoriteSegment(movies.movieId).collectLatest {
//            isMovieFavorite.postValue(
//                if (it.isSuccess) {
//                    val state = it.getOrDefault(false)
//                    state
//                } else {
//                    false
//                }
//            )
//        }
//    }

//    fun getFavorite():LiveData<Boolean> = isMovieFavorite

//    fun saveToFavorite() = liveData {
//        if(isMovieFavorite.value == false){
//            emitSource(favoriteRepo.insertMoviesFavorite(movies).asLiveData())
//        }else {
//            emitSource(favoriteRepo.deleteMovieFavorite(movies).asLiveData())
//        }
//    }
}