package made.dicoding.moviecatalogueapps.presentation.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import made.dicoding.moviecatalogueapps.presentation.home.movies.MoviesFragment
import made.dicoding.moviecatalogueapps.presentation.home.tvshow.TvShowFragment

class HomePagerAdapter(
    fm:FragmentManager,
    lifecycle: Lifecycle
):FragmentStateAdapter(fm,lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> MoviesFragment()
            1-> TvShowFragment()
            else->Fragment()
        }
    }


}