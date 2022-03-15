package made.dicoding.moviecatalogueapps.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import made.dicoding.moviecatalogueapps.core.domain.use_case.movies.IMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import made.dicoding.moviecatalogueapps.core.model.ListMovies
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: IMoviesUseCase
):ViewModel() {
    private val loaderMovie = MutableLiveData<Boolean>()
    fun getLoaderMovie():LiveData<Boolean> = loaderMovie
    private val exceptionMovies = MutableLiveData<Pair<Boolean,String?>>()
    fun errorDataMovies():LiveData<Pair<Boolean,String?>> = exceptionMovies

    private val loaderTvShows = MutableLiveData<Boolean>()
    fun getLoaderTvShows():LiveData<Boolean> = loaderTvShows
    private val exceptionTvShows = MutableLiveData<Pair<Boolean,String?>>()
    fun errorDataTvShows():LiveData<Pair<Boolean,String?>> = exceptionTvShows

    private val movies = MutableLiveData<List<ListMovies>>()
    fun getMovies():LiveData<List<ListMovies>> = movies
    fun getMovieList() = viewModelScope.launch {
        loaderMovie.postValue(true)
        useCase.getMovieList()
            .onEach { result->
                loaderMovie.postValue(false)
                exceptionMovies.postValue(Pair(false,null))
                if(result.isSuccess){
                    movies.postValue(result.getOrNull())
                }else{
                    exceptionMovies.postValue(Pair(true,result.exceptionOrNull()?.message))
                }
            }.launchIn(this)
    }

    private val tvShows = MutableLiveData<List<ListMovies>>()
    fun getTvShows():LiveData<List<ListMovies>> = tvShows
    fun getTvShowList() = viewModelScope.launch {
        loaderTvShows.postValue(true)
        useCase.getTvShowList()
            .onEach { result->
                loaderTvShows.postValue(false)
                exceptionTvShows.postValue(Pair(false,null))
                if(result.isSuccess){
                    tvShows.postValue(result.getOrNull())
                }else{
                    exceptionTvShows.postValue(Pair(true,result.exceptionOrNull()?.message))
                }
            }.launchIn(this)
    }
}