package made.dicoding.moviecatalogueapps.core.utils.mapper

import made.dicoding.moviecatalogueapps.core.model.DetailMovie
import made.dicoding.moviecatalogueapps.core.model.Movies
import javax.inject.Inject

class MovieMapper @Inject constructor():Function1<Movies?,DetailMovie> {
    override fun invoke(raw: Movies?): DetailMovie {
        return DetailMovie(
            raw?.movieId,
            raw?.title,
            raw?.tagline,
            raw?.poster,
            raw?.overview,
            raw?.userScore,
            raw?.releaseDate,
            raw?.category,
            raw?.urlWatch,
            raw?.productionCountry,
            raw?.originLanguage,
            raw?.companies,
            raw?.type
        )
    }
}