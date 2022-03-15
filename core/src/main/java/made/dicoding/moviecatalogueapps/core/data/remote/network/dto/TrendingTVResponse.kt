package made.dicoding.moviecatalogueapps.core.data.remote.network.dto

import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper
import made.dicoding.moviecatalogueapps.core.model.Movies
import com.google.gson.annotations.SerializedName

data class TrendingTvResponse(
    @SerializedName("results") var results: ArrayList<ResultsTvResponse> = arrayListOf(),
){
    fun toMovies():List<Movies>{
        val movies:ArrayList<Movies> = arrayListOf()
        results.map {
            movies.add(
                Movies(
                    movieId = it.id,
                    title = it.name,
                    poster = ConstanNameHelper.BASE_URL_IMAGE + it.posterPath,
                    releaseDate = it.firstAirDate,
                    userScore = it.voteAverage?.times(10)?.toInt(),
                    originLanguage = it.originalLanguage
                )
            )
        }
        return movies
    }
}

data class ResultsTvResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("original_name") var originalName: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("first_air_date") var firstAirDate: String? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
)