package made.dicoding.moviecatalogueapps.core.domain.use_case.favorite

import androidx.paging.PagingSource
import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity
import made.dicoding.moviecatalogueapps.core.domain.repository.IFavoriteRepository
import javax.inject.Inject


class FavoriteUseCaseImpl @Inject constructor(
    private val iFavoriteRepository: IFavoriteRepository
):IFavoriteUseCase {
    override fun getMovieByType(type: String): PagingSource<Int, FavoriteEntity> = iFavoriteRepository
        .getFavoriteByType(type)
}