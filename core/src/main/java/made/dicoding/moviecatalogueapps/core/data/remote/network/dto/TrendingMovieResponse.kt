package made.dicoding.moviecatalogueapps.core.data.remote.network.dto

import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper.BASE_URL_IMAGE
import made.dicoding.moviecatalogueapps.core.model.Movies
import com.google.gson.annotations.SerializedName

data class TrendingMovieResponse(
    @SerializedName("results") var results: List<ResultsMovieResponse>,
){
    fun toMovies():List<Movies>{
        val movies:ArrayList<Movies> = arrayListOf()
        results.map {
            movies.add(
                Movies(
                    movieId = it.id,
                    title = it.title,
                    poster = BASE_URL_IMAGE +it. posterPath,
                    releaseDate = it.releaseDate,
                    userScore = it.voteAverage?.times(10)?.toInt(),
                    originLanguage = it.originalLanguage
                )
            )
        }
        return movies
    }
}

data class ResultsMovieResponse(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
)