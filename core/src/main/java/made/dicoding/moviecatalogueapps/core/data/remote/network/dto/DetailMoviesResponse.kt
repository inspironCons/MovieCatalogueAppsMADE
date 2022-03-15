package made.dicoding.moviecatalogueapps.core.data.remote.network.dto

import com.google.gson.annotations.SerializedName
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper.BASE_URL_IMAGE
import made.dicoding.moviecatalogueapps.core.model.Companies
import made.dicoding.moviecatalogueapps.core.model.Genres
import made.dicoding.moviecatalogueapps.core.model.Movies

data class DetailMoviesResponse(
    @SerializedName("genres") var genres: ArrayList<GenresDto> = arrayListOf(),
    @SerializedName("homepage") var homepage: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("production_companies") var productionCompanies: ArrayList<ProductionCompanies> = arrayListOf(),
    @SerializedName("production_countries") var productionCountries: ArrayList<ProductionCountries> = arrayListOf(),
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("tagline") var tagline: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
){
    fun toMovies(): Movies {
        return Movies(
            movieId = id,
            title = title,
            tagline = tagline,
            poster = BASE_URL_IMAGE+posterPath,
            overview = overview,
            userScore = voteAverage?.times(10)?.toInt(),
            releaseDate=releaseDate,
            category = genres.map { it.toGenres() },
            urlWatch = homepage,
            productionCountry = productionCountries[0].iso31661,
            companies = productionCompanies.map { it.toCompanies() },
            originLanguage= originalLanguage
        )
    }
}

data class GenresDto(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null
){
    fun toGenres(): Genres {
        return Genres(
            id = id,
            name = name
        )
    }
}

data class ProductionCompanies(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("logo_path") var logoPath: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("origin_country") var originCountry: String? = null
){
    fun toCompanies(): Companies {
        return Companies(
            logos = BASE_URL_IMAGE+logoPath,
            name = name
        )
    }
}

data class ProductionCountries(

    @SerializedName("iso_3166_1") var iso31661: String? = null,
    @SerializedName("name") var name: String? = null

)


data class DetailTvShowsResponse(
    @SerializedName("first_air_date") var firstAirDate: String? = null,
    @SerializedName("genres") var genres: ArrayList<GenresDto> = arrayListOf(),
    @SerializedName("homepage") var homepage: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("production_companies") var productionCompanies: ArrayList<ProductionCompanies> = arrayListOf(),
    @SerializedName("production_countries") var productionCountries: ArrayList<ProductionCountries> = arrayListOf(),
    @SerializedName("tagline") var tagline: String? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
){
    fun toMovies(): Movies {
        return Movies(
            movieId = id,
            title = name,
            tagline = tagline,
            poster = BASE_URL_IMAGE+posterPath,
            overview = overview,
            userScore = voteAverage?.times(10)?.toInt(),
            releaseDate=firstAirDate,
            category = genres.map { it.toGenres() },
            urlWatch = homepage,
            productionCountry = productionCountries[0].iso31661,
            companies = productionCompanies.map { it.toCompanies() },
            originLanguage= originalLanguage
        )
    }
}


