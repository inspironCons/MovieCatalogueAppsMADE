package made.dicoding.moviecatalogueapps.favorite

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItem
import made.dicoding.moviecatalogueapps.core.utils.General
import made.dicoding.moviecatalogueapps.core.utils.General.toGetYear
import made.dicoding.moviecatalogueapps.utils.BaseUiTest
import made.dicoding.moviecatalogueapps.utils.GeneralTesting
import org.junit.Test
import made.dicoding.moviecatalogueapps.R as App
import made.dicoding.moviecatalogueapps.core.R as Core
class FavoriteFeatures: BaseUiTest() {
    private val mockDataMovies = General.dummyDataMoviesType()
    private val mockTvShows = General.dummyDataTvShowsType()
    private val general = GeneralTesting()
    @Test
    fun navigateToFavoriteList(){
        clickOn(App.id.btn_favorite)
        assertDisplayed(R.id.favorite_layout)
    }
    private fun insertData(){
        clickListItem(App.id.rv_movies, moviePosition)
        clickOn(App.id.fab_favorite)
        clickOn(App.id.btn_back)
        clickOn(App.id.btn_favorite)
    }
    @Test
    fun displayFavoriteMovies(){
        insertData()
        onView(ViewMatchers.withId(R.id.tl_favorite)).perform(general.selectTabAtPosition(0))
        val years = mockDataMovies.releaseDate?.toGetYear()
        assertDisplayedAtPosition(
            App.id.rv_movies,
            moviePosition,
            Core.id.movie_title,
            "${mockDataMovies.title} ($years)"
        )
        assertDisplayedAtPosition(
            App.id.rv_movies,
            moviePosition,
            Core.id.movie_score,
            "${mockDataMovies.userScore}%"
        )
        assertDisplayedAtPosition(
            App.id.rv_movies,
            moviePosition,
            Core.id.movie_ori_language,
           "${mockDataMovies.originLanguage?.uppercase()}"
        )

    }

    @Test
    fun displayFavoriteTvShows(){
        onView(ViewMatchers.withId(App.id.tl_home)).perform(general.selectTabAtPosition(1))
        clickListItem(App.id.rv_tv_show, tvShowPosition)
        clickOn(App.id.fab_favorite)
        clickOn(App.id.btn_back)
        clickOn(App.id.btn_favorite)
        onView(ViewMatchers.withId(R.id.tl_favorite)).perform(general.selectTabAtPosition(1))
        val years = mockTvShows.releaseDate?.toGetYear()
        assertDisplayedAtPosition(
            App.id.rv_tv_show,
            tvShowPosition,
            Core.id.movie_title,
            "${mockTvShows.title} ($years)"
        )
        assertDisplayedAtPosition(
            App.id.rv_tv_show,
            tvShowPosition,
            Core.id.movie_score,
            "${mockTvShows.userScore}%"
        )
        assertDisplayedAtPosition(
            App.id.rv_tv_show,
            tvShowPosition,
            Core.id.movie_ori_language,
            "${mockTvShows.originLanguage?.uppercase()}"
        )

    }

    @Test
    fun checkDetailMovieFromIntentFavorite(){
        insertData()
        clickListItem(App.id.rv_movies,0)
        assertDisplayed(App.id.detail_activity)
    }
}