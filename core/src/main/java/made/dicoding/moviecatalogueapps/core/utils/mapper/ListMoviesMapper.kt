package made.dicoding.moviecatalogueapps.core.utils.mapper

import made.dicoding.moviecatalogueapps.core.model.ListMovies
import made.dicoding.moviecatalogueapps.core.model.Movies
import javax.inject.Inject

class ListMoviesMapper @Inject constructor():Function1<List<Movies>?,List<ListMovies>> {
    override fun invoke(raw: List<Movies>?): List<ListMovies> {
        return raw?.map { movie->
            ListMovies(
                movieId = movie.movieId,
                title = movie.title,
                poster = movie.poster,
                releaseDate = movie.releaseDate,
                voteAverage = movie.userScore,
                originLanguage = movie.originLanguage
            )
        }?: arrayListOf()
    }
}