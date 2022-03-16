package made.dicoding.moviecatalogueapps.core.domain.use_case.favorite

import androidx.paging.PagingSource
import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity

interface IFavoriteUseCase {
    fun getMovieByType(type:String): PagingSource<Int, FavoriteEntity>
}