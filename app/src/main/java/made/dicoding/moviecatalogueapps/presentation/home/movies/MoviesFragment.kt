package made.dicoding.moviecatalogueapps.presentation.home.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import made.dicoding.moviecatalogueapps.R
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper
import made.dicoding.moviecatalogueapps.core.model.ListMovies
import made.dicoding.moviecatalogueapps.core.presentation.MoviesAdapter
import made.dicoding.moviecatalogueapps.core.utils.General.isShowComponentProgress
import made.dicoding.moviecatalogueapps.core.utils.General.showEmptyState
import made.dicoding.moviecatalogueapps.databinding.FragmentMoviesBinding
import made.dicoding.moviecatalogueapps.presentation.detail.DetailMovieActivity
import made.dicoding.moviecatalogueapps.presentation.home.HomeViewModel

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get()=_binding

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity != null){
            viewModel.getMovieList()
            loaderShowAndHide()
            generateRvMovies()
            showMessage()
        }
    }


    private fun loaderShowAndHide(){
        viewModel.getLoaderMovie().observe(viewLifecycleOwner){ show->
            isShowComponentProgress(binding?.loadContentMovies,show)
        }
    }

    private fun generateRvMovies() {
        with(binding){
            val adapter = MoviesAdapter()
            this?.rvMovies?.layoutManager = LinearLayoutManager(context)
            this?.rvMovies?.setHasFixedSize(true)

            viewModel.getMovies().observe(viewLifecycleOwner){ movies->
                adapter.setMovies(movies)
                binding?.rvMovies?.adapter = adapter
            }

            adapter.setOnClickListener(object : MoviesAdapter.ItemsCallback{
                override fun onClickItem(movie: ListMovies) {
                    val mIntent = Intent(context, DetailMovieActivity::class.java)
                    mIntent.putExtra(DetailMovieActivity.idMovie,movie.movieId)
                    mIntent.putExtra(DetailMovieActivity.type, ConstanNameHelper.MOVIES_TYPE)
                    mIntent.putExtra(DetailMovieActivity.local, false)
                    startActivity(mIntent)
                }
            })
        }
    }


    private fun showMessage(){
        viewModel.errorDataMovies().observe(viewLifecycleOwner){ show->
            showEmptyState(show.first,binding?.errorState)
            binding?.errorState?.setDescription(show.second?:resources.getString(R.string.msg_error_default))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}