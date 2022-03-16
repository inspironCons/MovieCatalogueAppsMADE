package made.dicoding.moviecatalogueapps.presentation.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.text.LineBreaker
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.AndroidEntryPoint
import made.dicoding.moviecatalogueapps.R
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper
import made.dicoding.moviecatalogueapps.core.model.Companies
import made.dicoding.moviecatalogueapps.core.presentation.CompaniesAdapter
import made.dicoding.moviecatalogueapps.core.utils.General.toDateFormatRelease
import made.dicoding.moviecatalogueapps.databinding.ActivityDetailMovieBinding

@AndroidEntryPoint
class DetailMovieActivity:AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding

    private val viewModel: DetailMovieViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDetail()
        clickFavorite()
        onBackNav()
    }

    private fun onBackNav() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun getDetail() {
        val extra = intent.extras
        if(extra != null){
            val id = extra.getInt(idMovie)
            val type = extra.getString(type) as String
            val local = extra.getBoolean(local)
            if(type == ConstanNameHelper.TV_TYPE){
                binding.tvLableCompanies.text = getString(R.string.network)
            }else{
                binding.tvLableCompanies.text = getString(R.string.companies)
            }
            viewModel.detailMovie(id,type,local).observe(this){ data->
                val movie = data.getOrNull()
                viewModel.setDetailMovie(movie,type)
                viewModel.checkFavorite()
                checkIsFavorite()

                with(binding){
                    Glide.with(this@DetailMovieActivity)
                        .asBitmap()
                        .load(movie?.poster)
                        .apply(RequestOptions().error(R.drawable.movie_poster_default))
                        .into(object : CustomTarget<Bitmap>(1280, 720){
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?
                            ) {
                                moviePosterDetail.setImageBitmap(resource)

                                Palette.Builder(resource).generate { palette->
                                    val dominantColor = palette?.getDominantColor(ContextCompat.getColor(this@DetailMovieActivity,R.color.primary_color))
                                    val window = window
                                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                                    if(dominantColor != null) window.statusBarColor = dominantColor
                                }
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                            }
                        })

                    movieTitleDetail.text = movie?.title
                    movieTagline.text = getString(R.string.tagline,movie?.tagline)
                    movieUserScore.text = getString(R.string.movie_score,movie?.userScore)
                    movieReleaseDate.text = movie?.releaseDate?.toDateFormatRelease()
                    movieProductionCountry.text = movie?.productionCountry
                    movieCategory.text = movie?.category?.joinToString(",")

                    movieOverview.text = movie?.overview
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                        movieOverview.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
                    }

                    navigateToWatch(movie?.urlWatch)
                    generateCast(movie?.companies)
                }
            }
        }

    }

    private fun checkIsFavorite(){
        viewModel.getFavorite().observe(this){state->
            if(state){
                binding.fabFavorite.imageTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(this,
                        R.color.pink))
            }else{
                binding.fabFavorite.imageTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(this,
                        R.color.white))
            }
        }
    }

    private fun clickFavorite(){
        binding.fabFavorite.setOnClickListener {
            viewModel.saveToFavorite().observe(this){ msg->
                var toast: Toast? = null
                toast?.cancel()
                toast = Toast.makeText(this@DetailMovieActivity, msg.getOrDefault(""),Toast.LENGTH_SHORT)
                toast?.show()
                viewModel.checkFavorite()
            }
        }
    }

    private fun generateCast(companies: List<Companies>?) {
        val adapter = CompaniesAdapter()
        adapter.setCompanies(companies)
        with(binding){
            rvCompanies.layoutManager = LinearLayoutManager(this@DetailMovieActivity,LinearLayoutManager.HORIZONTAL,false)
            rvCompanies.adapter = adapter
        }
    }

    private fun navigateToWatch(urlWatch: String?) {
        binding.btnWatch.setOnClickListener {
            if(urlWatch != null){
                val mIntent = Intent(Intent.ACTION_VIEW)
                mIntent.data = Uri.parse(urlWatch)
                startActivity(mIntent)
            }
        }
    }

    companion object{
        const val idMovie = "idMovie"
        const val type = "TYPE"
        const val local = "isLocal"
    }
}