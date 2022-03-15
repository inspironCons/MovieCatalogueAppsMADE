package made.dicoding.moviecatalogueapps.core.model

data class ListMovies (
    var movieId:Int? = 0,
    var title:String? = "",
    var poster:String? = "",
    var releaseDate:String? = "",
    var voteAverage:Int? = 0,
    var originLanguage:String? = "",
)