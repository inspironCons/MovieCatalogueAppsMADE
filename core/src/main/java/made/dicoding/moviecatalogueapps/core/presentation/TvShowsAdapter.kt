package made.dicoding.moviecatalogueapps.core.presentation

import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.TypefaceSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import made.dicoding.moviecatalogueapps.core.R
import made.dicoding.moviecatalogueapps.core.databinding.ItemsMoviesBinding
import made.dicoding.moviecatalogueapps.core.model.ListMovies
import made.dicoding.moviecatalogueapps.core.utils.General.toGetYear

class TvShowsAdapter:RecyclerView.Adapter<TvShowsAdapter.ViewHolder>() {
    private var listTvShow = ArrayList<ListMovies>()
    private lateinit var itemCallback: ItemsCallback

    inner class ViewHolder(private val binding: ItemsMoviesBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(movie:ListMovies){
            with(binding){
                Glide.with(itemView.context)
                    .load(movie.poster)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_image_loader).error(R.drawable.ic_empty_poster))
                    .into(moviePoster)

                val title = itemView.context.getString(R.string.movie_title_and_years,movie.title,"(${movie.releaseDate?.toGetYear()})")
                val spanText = SpannableString(title)
                val thinTextLength = title.length - 6
                val size = AbsoluteSizeSpan(14,true)
                spanText.setSpan(size,thinTextLength,title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    val typeFace = Typeface.create(ResourcesCompat.getFont(itemView.context,R.font.roboto_thin),Typeface.NORMAL)
                    spanText.setSpan(TypefaceSpan(typeFace),thinTextLength,title.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    movieTitle.text = spanText
                }else{
                    movieTitle.text = spanText
                }

                movieScore.text = itemView.context.getString(R.string.movie_score,movie.voteAverage)
                movieScoreGraph.progress = movie.voteAverage ?:0
                movieOriLanguage.text = movie.originLanguage?.uppercase()


                itemView.setOnClickListener { itemCallback.onClickItem(movie) }
            }
        }
    }

    fun setMovies(list:List<ListMovies>?){
        if(list.isNullOrEmpty())return
        this.listTvShow.clear()
        this.listTvShow.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemsViewBinding = ItemsMoviesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemsViewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listTvShow[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listTvShow.size
    fun setOnClickListener(itemCallback: ItemsCallback){
        this.itemCallback = itemCallback
    }

    interface ItemsCallback{
        fun onClickItem(movie:ListMovies)
    }
}