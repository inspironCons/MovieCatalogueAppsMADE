package made.dicoding.moviecatalogueapps.core.model

data class DetailMovie(
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
)
