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

    fun dummyDataMoviesType(): FavoriteEntity {
        return FavoriteEntity(
            movieId = 634649,
            title = "Spider-Man: No Way Home",
            tagline = "The Multiverse unleashed.",
            poster = ConstanNameHelper.BASE_URL_IMAGE +"/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            overview = "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
            userScore = 85,
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
            title = "The Book of Boba Fett",
            tagline = "Every galaxy has an underworld.",
            poster = ConstanNameHelper.BASE_URL_IMAGE +"/gNbdjDi1HamTCrfvM9JeA94bNi2.jpg",
            overview = "Legendary bounty hunter Boba Fett and mercenary Fennec Shand must navigate the galaxy’s underworld when they return to the sands of Tatooine to stake their claim on the territory once ruled by Jabba the Hutt and his crime syndicate.",
            userScore = 81,
            releaseDate="2021-12-29",
            category = listOf(
                Genres(10759,"Action & Adventure"),
                Genres(10765,"Sci-Fi & Fantasy")
            ),
            urlWatch = "https://www.disneyplus.com/series/the-book-of-boba-fett/57TL7zLNu2wf",
            productionCountry = "US",
            companies = listOf(
                Companies(ConstanNameHelper.BASE_URL_IMAGE+"/o86DbpburjxrqAzEDhXZcyE8pDb.png", "Lucasfilm"),
                Companies(ConstanNameHelper.BASE_URL_IMAGE+"/cfyTfcjSd9njenirn3d07xqwrZQ.png", "Golem Creations"),
                Companies(ConstanNameHelper.BASE_URL_IMAGE+"/rRGi5UkwvdOPSfr5Xf42RZUsYgd.png", "Walt Disney Television"),
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