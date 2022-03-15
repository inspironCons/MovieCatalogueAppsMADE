package made.dicoding.moviecatalogueapps.core.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import made.dicoding.moviecatalogueapps.core.R
import made.dicoding.moviecatalogueapps.core.databinding.ItemsCompaniesBinding
import made.dicoding.moviecatalogueapps.core.model.Companies

class CompaniesAdapter: RecyclerView.Adapter<CompaniesAdapter.ViewHolder>() {
    private val listCompanies = ArrayList<Companies>()
    inner class ViewHolder(private val binding: ItemsCompaniesBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(cast:Companies){
            with(binding){
                Glide.with(itemView.context)
                    .load(cast.logos)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_image_loader).error(R.drawable.cast_image_default))
                    .into(logoCompanies)

                nameCompanies.text = cast.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsCompaniesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listCompanies[position])
    }

    override fun getItemCount(): Int = listCompanies.size

    fun setCompanies(cast:List<Companies>?){
        if(cast.isNullOrEmpty()) return
        this.listCompanies.clear()
        this.listCompanies.addAll(cast)
    }

}