package made.dicoding.moviecatalogueapps.core.data.remote.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper
import made.dicoding.moviecatalogueapps.core.model.Companies
import made.dicoding.moviecatalogueapps.core.model.Genres
import made.dicoding.moviecatalogueapps.core.model.Movies

@Entity(tableName = "favorite_movie")
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var movieId:Int? = 0,
    @ColumnInfo(name = "title")
    var title:String? = "",
    @ColumnInfo(name = "tagline")
    var tagline:String? = "",
    @ColumnInfo(name = "poster")
    var poster:String? = "",
    @ColumnInfo(name = "overview")
    var overview:String? = "",
    @ColumnInfo(name = "user_score")
    var userScore:Int? = 0,
    @ColumnInfo(name = "release_date")
    var releaseDate:String? = "",
    @ColumnInfo(name = "category")
    var category:List<Genres>? = arrayListOf(),
    @ColumnInfo(name = "url_watch")
    var urlWatch:String? = "",
    @ColumnInfo(name = "production_country")
    var productionCountry:String?="",
    @ColumnInfo(name = "origin_language")
    var originLanguage:String? = "",
    @ColumnInfo(name = "companies")
    var companies:List<Companies>? = arrayListOf(),
    @ColumnInfo(name = "type")
    var type:String? = ""
){
    fun toMovies(): Movies {
        return Movies(
            movieId = movieId,
            title = title,
            tagline = tagline,
            poster = ConstanNameHelper.BASE_URL_IMAGE +poster,
            overview = overview,
            userScore = userScore,
            releaseDate=releaseDate,
            category = category,
            urlWatch = urlWatch,
            productionCountry = productionCountry,
            companies = companies,
            originLanguage= originLanguage
        )
    }
}

