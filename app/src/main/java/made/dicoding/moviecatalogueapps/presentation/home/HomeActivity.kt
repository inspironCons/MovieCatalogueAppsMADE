package made.dicoding.moviecatalogueapps.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import made.dicoding.moviecatalogueapps.R
import made.dicoding.moviecatalogueapps.databinding.ActivityHomeBinding
import made.dicoding.moviecatalogueapps.presentation.favorite.FavoriteActivity

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.elevation = 0f
        initTabsView()
        navigateToFavorite()
    }

    private fun initTabsView(){
        val adapter = HomePagerAdapter(supportFragmentManager,lifecycle)
        binding.vpHome.adapter = adapter
        TabLayoutMediator(binding.tlHome,binding.vpHome){tabs,position->
            tabs.text = when(position){
                0-> getString(R.string.movie_text)
                1-> getString(R.string.tv_show_text)
                else -> ""
            }
        }.attach()
    }

    private fun navigateToFavorite(){
        binding.btnFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
    }

    override fun onBackPressed() {
        if(binding.tlHome.selectedTabPosition>0){
            binding.tlHome.getTabAt(0)?.select()
        }else{
            super.onBackPressed()
        }
    }
}