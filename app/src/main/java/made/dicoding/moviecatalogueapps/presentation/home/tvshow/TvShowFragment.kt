package made.dicoding.moviecatalogueapps.presentation.home.tvshow

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
import made.dicoding.moviecatalogueapps.core.presentation.TvShowsAdapter
import made.dicoding.moviecatalogueapps.core.utils.General
import made.dicoding.moviecatalogueapps.core.utils.General.isShowComponentProgress
import made.dicoding.moviecatalogueapps.databinding.FragmentTvShowBinding
import made.dicoding.moviecatalogueapps.presentation.detail.DetailMovieActivity
import made.dicoding.moviecatalogueapps.presentation.home.HomeViewModel

@AndroidEntryPoint
class TvShowFragment : Fragment() {

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding

    private val viewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvShowBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity != null){
            viewModel.getTvShowList()
            showHideLoader()
            generateTvShows()
            showMessage()
        }
    }

    private fun showHideLoader(){
        viewModel.getLoaderTvShows().observe(viewLifecycleOwner){ show->
            isShowComponentProgress(binding?.loadContentTvShow,show)
        }
    }

    private fun generateTvShows() {
        with(binding){
            val adapter = TvShowsAdapter()

            this?.rvTvShow?.layoutManager = LinearLayoutManager(context)
            this?.rvTvShow?.setHasFixedSize(true)

            viewModel.getTvShows().observe(viewLifecycleOwner){ tvShows->
                adapter.setMovies(tvShows)
                this?.rvTvShow?.adapter = adapter
            }

            adapter.setOnClickListener(object : TvShowsAdapter.ItemsCallback{
                override fun onClickItem(movie: ListMovies) {
                    val mIntent = Intent(context, DetailMovieActivity::class.java)
                    mIntent.putExtra(DetailMovieActivity.idMovie,movie.movieId)
                    mIntent.putExtra(DetailMovieActivity.type, ConstanNameHelper.TV_TYPE)
                    mIntent.putExtra(DetailMovieActivity.local, false)
                    startActivity(mIntent)
                }
            })
        }
    }

    private fun showMessage(){
        viewModel.errorDataTvShows().observe(viewLifecycleOwner){ show->
            General.showEmptyState(show.first, binding?.errorState)
            binding?.errorState?.setDescription(show.second?:resources.getString(R.string.msg_error_default))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}