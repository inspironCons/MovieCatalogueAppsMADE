package made.dicoding.moviecatalogueapps.presentation.detail

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper.MOVIES_TYPE
import made.dicoding.moviecatalogueapps.core.domain.use_case.movie.IMovieUseCase
import made.dicoding.moviecatalogueapps.core.model.DetailMovie
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val useCase: IMovieUseCase
): ViewModel() {
    var movies: DetailMovie = DetailMovie()
    var isMovieFavorite = MutableLiveData<Boolean>()

    fun setDetailMovie(data: DetailMovie?, type:String){
       if(data != null){
           movies = data
           movies.type = type
       }
    }
    fun detailMovie(id:Int,type:String,getSourceLocal:Boolean) =  liveData(Dispatchers.IO) {
        if(!getSourceLocal) {
            when (type) {
                MOVIES_TYPE -> emitSource(useCase.getDetailMovie(id).asLiveData())
                else -> emitSource(useCase.getDetailTvShow(id).asLiveData())
            }
        }else{
            emitSource(useCase.getMovieFromSourceLocal(id).asLiveData())
        }
    }

    fun checkFavorite() = viewModelScope.launch {
        movies.movieId?.let { id->
            useCase.isMovieAlreadyInFavorite(id).collectLatest {
                isMovieFavorite.postValue(
                    if (it.isSuccess) {
                        val state = it.getOrDefault(false)
                        state
                    } else {
                        false
                    }
                )
            }
        }
    }

    fun getFavorite():LiveData<Boolean> = isMovieFavorite

    fun saveToFavorite() = liveData {
        if(isMovieFavorite.value == false){
            emitSource(useCase.insertMovieToFavorite(movies).asLiveData())
        }else {
            emitSource(useCase.deleteMovieFromFavorite(movies).asLiveData())
        }
    }
}