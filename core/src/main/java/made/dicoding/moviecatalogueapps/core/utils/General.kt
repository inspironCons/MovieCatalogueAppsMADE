package made.dicoding.moviecatalogueapps.core.utils

import android.annotation.SuppressLint
import android.view.View
import android.widget.ProgressBar
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper
import made.dicoding.moviecatalogueapps.core.component.ErrorState
import made.dicoding.moviecatalogueapps.core.data.remote.local.entity.FavoriteEntity
import made.dicoding.moviecatalogueapps.core.model.Companies
import made.dicoding.moviecatalogueapps.core.model.Genres
import made.dicoding.moviecatalogueapps.core.model.ListMovies
import made.dicoding.moviecatalogueapps.core.model.Movies
import java.text.SimpleDateFormat
import java.util.*

object General {
    @SuppressLint("SimpleDateFormat")
    fun String.toGetYear():String{
        val date = SimpleDateFormat("yyyy-MM-dd", Locale("IND","ID")).parse(this)
        return SimpleDateFormat("yyyy").format(date as Date)
    }

    @SuppressLint("SimpleDateFormat")
    fun String.toDateFormatRelease():String{
        val date = SimpleDateFormat("yyyy-MM-dd", Locale("IND","ID")).parse(this)
        return SimpleDateFormat("dd MMM  yyyy").format(date as Date)
    }


    fun isShowComponentProgress(view:ProgressBar?,show:Boolean){
        if(show){
            view?.visibility = View.VISIBLE
        }else{
            view?.visibility = View.GONE
        }
    }

    fun dummyMoviesList() = ListMovies(
        movieId = 634649,
        title = "Spider-Man: No Way Home",
        poster = ConstanNameHelper.BASE_URL_IMAGE+"/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
        releaseDate = "2021-12-15",
        voteAverage = (8.2 * 10.0).toInt(),
        originLanguage = "en"
    )

    fun dummyTvShowsList() = ListMovies(
        movieId = 115036,
        title = "Euphoria",
        poster = ConstanNameHelper.BASE_URL_IMAGE+"/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg",
        releaseDate = "2019-06-16",
        voteAverage = (8.4 * 10.0).toInt(),
        originLanguage = "en"
    )

    fun dummyDataMoviesType(): FavoriteEntity {
        return FavoriteEntity(
            movieId = 634649,
            title = "Spider-Man: No Way Home",
            tagline = "The Multiverse unleashed.",
            poster = ConstanNameHelper.BASE_URL_IMAGE +"/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            overview = "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
            userScore = 82,
            releaseDate="2021-12-15",
            category = listOf(
                Genres(28,"Action"),
                Genres(12,"Adventure"),
                Genres(878,"Science Fiction")
            ),
            urlWatch = "https://www.spidermannowayhome.movie",
            productionCountry = "US",
            companies = listOf(
                Companies(ConstanNameHelper.BASE_URL_IMAGE+"/hUzeosd33nzE5MCNsZxCGEKTXaQ.png", "Marvel Studios"),
                Companies(ConstanNameHelper.BASE_URL_IMAGE+"/nw4kyc29QRpNtFbdsBHkRSFavvt.png", "Pascal Pictures"),
                Companies(ConstanNameHelper.BASE_URL_IMAGE+"/71BqEFAF4V3qjjMPCpLuyJFB9A.png", "Columbia Pictures"),
            ),
            type = ConstanNameHelper.MOVIES_TYPE,
            originLanguage = "EN"
        )
    }

    fun dummyDataTvShowsType(): FavoriteEntity {
        return FavoriteEntity(
            movieId = 115036,
            title = "Euphoria",
            tagline = "Remember this feeling.",
            poster = ConstanNameHelper.BASE_URL_IMAGE +"/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg",
            overview = "A group of high school students navigate love and friendships in a world of drugs, sex, trauma, and social media.",
            userScore = 84,
            releaseDate="2019-06-16",
            category = listOf(
                Genres(18,"Drama")
            ),
            urlWatch = "https://www.hbo.com/euphoria",
            productionCountry = "US",
            companies = listOf(
                Companies(ConstanNameHelper.BASE_URL_IMAGE+"/1ZXsGaFPgrgS6ZZGS37AqD5uU12.png", "A24"),
                Companies(ConstanNameHelper.BASE_URL_IMAGE+"null", "The Reasonable Bunch"),
                Companies(ConstanNameHelper.BASE_URL_IMAGE+"null", "Little Lamb Productions"),
                Companies(ConstanNameHelper.BASE_URL_IMAGE+"null", "DreamCrew"),
            ),
            type = ConstanNameHelper.TV_TYPE,
            originLanguage = "EN"
        )
    }

    fun showEmptyState(state:Boolean,view: ErrorState?){
        if(state){
            view?.visibility = View.VISIBLE
        }else{
            view?.visibility = View.GONE
        }
    }
}