package made.dicoding.moviecatalogueapps.favorite.presentation.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import made.dicoding.moviecatalogueapps.R
import made.dicoding.moviecatalogueapps.favorite.databinding.ActivityFavoriteBinding

class FavoriteActivity:AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.elevation = 0f
        initTabsView()
        onBackNav()
    }

    private fun initTabsView(){
        val adapter = FavoritePagerAdapter(supportFragmentManager,lifecycle)
        binding.vpFavorite.adapter = adapter
        TabLayoutMediator(binding.tlFavorite,binding.vpFavorite){tabs,position->
            tabs.text = when(position){
                0-> getString(R.string.movie_text)
                1-> getString(R.string.tv_show_text)
                else -> ""
            }
        }.attach()
    }
    private fun onBackNav() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        if(binding.tlFavorite.selectedTabPosition>0){
            binding.tlFavorite.getTabAt(0)?.select()
        }else{
            super.onBackPressed()
        }
    }
}