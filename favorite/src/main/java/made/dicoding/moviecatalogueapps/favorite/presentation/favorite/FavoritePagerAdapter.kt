package made.dicoding.moviecatalogueapps.favorite.presentation.favorite

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import made.dicoding.moviecatalogueapps.favorite.presentation.favorite.movies.MoviesFavoriteFragment
import made.dicoding.moviecatalogueapps.favorite.presentation.favorite.tvshows.TvShowsFavoriteFragment

class FavoritePagerAdapter(
    fm:FragmentManager,
    lifecycle: Lifecycle
):FragmentStateAdapter(fm,lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> MoviesFavoriteFragment()
            1-> TvShowsFavoriteFragment()
            else->Fragment()
        }
    }


}