package made.dicoding.moviecatalogueapps.favorite.presentation.favorite.movies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper
import made.dicoding.moviecatalogueapps.core.model.ListMovies
import made.dicoding.moviecatalogueapps.core.presentation.FavoriteAdapter
import made.dicoding.moviecatalogueapps.databinding.FragmentMoviesBinding
import made.dicoding.moviecatalogueapps.di.AppComponent
import made.dicoding.moviecatalogueapps.favorite.di.DaggerFavoriteDI
import made.dicoding.moviecatalogueapps.favorite.presentation.favorite.FavoriteViewModel
import made.dicoding.moviecatalogueapps.favorite.presentation.favorite.FavoriteViewModelFactory
import made.dicoding.moviecatalogueapps.presentation.detail.DetailMovieActivity
import javax.inject.Inject

class MoviesFavoriteFragment:Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get()=_binding

    @Inject
    lateinit var factory: FavoriteViewModelFactory
    private val viewModel: FavoriteViewModel by activityViewModels{
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteDI.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(activity?.applicationContext as Context, AppComponent::class.java)
            ).build().inject(this)
        super.onCreate(savedInstanceState)

    }
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
            getMovieList()
        }
    }

    private fun getMovieList() {
        val adapter = FavoriteAdapter()
        binding?.rvMovies?.layoutManager = LinearLayoutManager(context)
        binding?.rvMovies?.setHasFixedSize(true)
        binding?.rvMovies?.adapter = adapter
        lifecycleScope.launch {
            viewModel.getMovieByType(ConstanNameHelper.MOVIES_TYPE).collectLatest { data->
                adapter.submitData(data)
            }
        }

        adapter.setOnClickListener(object : FavoriteAdapter.ItemsCallback{
            override fun onClickItem(movie: ListMovies?) {
                val mIntent = Intent(context, DetailMovieActivity::class.java)
                mIntent.putExtra(DetailMovieActivity.idMovie,movie?.movieId)
                mIntent.putExtra(DetailMovieActivity.type, ConstanNameHelper.MOVIES_TYPE)
                mIntent.putExtra(DetailMovieActivity.local, true)

                startActivity(mIntent)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}