package made.dicoding.moviecatalogueapps.core.model

import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity

data class Movies(
    var movieId:Int? = 0,
    var title:String? = "",
    var tagline:String? = "",
    var poster:String? = "",
    var overview:String? = "",
    var userScore:Int? = 0,
    var releaseDate:String? = "",
    var category:List<Genres>? = arrayListOf(),
    var urlWatch:String? = "",
    var productionCountry:String?="",
    var originLanguage:String? = "",
    var companies:List<Companies>? = arrayListOf(),
    var type:String? = ""
){
    fun toListMovies():ListMovies{
        return ListMovies(
            movieId = movieId,
            title = title,
            poster = poster,
            releaseDate = releaseDate,
            voteAverage = userScore,
            originLanguage = originLanguage
        )
    }

    fun toDetailMovie(): DetailMovie {
        return DetailMovie(
            movieId,
            title,
            tagline,
            poster,
            overview,
            userScore,
            releaseDate,
            category,
            urlWatch,
            productionCountry,
            originLanguage,
            companies,
            type
        )
    }

    fun toFavoriteEntity():FavoriteEntity{
        return FavoriteEntity(
            movieId,
            title,
            tagline,
            poster,
            overview,
            userScore,
            releaseDate,
            category,
            urlWatch,
            productionCountry,
            originLanguage,
            companies,
            type
        )
    }
}

