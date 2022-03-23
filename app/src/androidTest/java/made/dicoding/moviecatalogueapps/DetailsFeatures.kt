package made.dicoding.moviecatalogueapps

import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.adevinta.android.barista.assertion.BaristaImageViewAssertions.assertHasAnyDrawable
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItem
import kotlinx.coroutines.runBlocking
import made.dicoding.moviecatalogueapps.core.utils.General
import made.dicoding.moviecatalogueapps.core.utils.General.toDateFormatRelease
import made.dicoding.moviecatalogueapps.utils.BaseUiTest
import made.dicoding.moviecatalogueapps.utils.GeneralTesting
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Test


class DetailsFeatures: BaseUiTest() {
    private val numTestingPosition = 0
    private val mockDataMovies = General.dummyDataMoviesType()

    private val general = GeneralTesting()

    @Before
    fun setDb(){
        this.createDbFavorite()
    }

    @After
    fun dbClose(){
        this.db.close()
    }

    @Test
    fun loadDetailsMovies(){
        clickListItem(R.id.rv_movies, numTestingPosition)
        assertDisplayed(R.id.detail_activity)
        //display  poster
        assertHasAnyDrawable(R.id.movie_poster_detail)
        // display title
        assertDisplayed(R.id.movie_title_detail,mockDataMovies.title?:"")
        // display tagline
        assertDisplayed(R.id.movie_tagline,"\" ${mockDataMovies.tagline} \"")
        // display release date
        assertDisplayed(R.id.movie_release_date,mockDataMovies.releaseDate?.toDateFormatRelease()?:"")
        // display user score
        assertDisplayed(R.id.movie_user_score,"${mockDataMovies.userScore}%")
        // display production country
        assertDisplayed(R.id.movie_production_country,mockDataMovies.productionCountry?:"")
        // display category/ Genres
        assertDisplayed(R.id.movie_category,mockDataMovies.category?.joinToString(",")?:"")
        // display overview
        assertDisplayed(R.id.movie_overview,mockDataMovies.overview?:"")
        // display size rv companies
        assertRecyclerViewItemCount(R.id.rv_companies,mockDataMovies.companies?.size?:0)
    }

    @Test
    fun navigateToIntentWhileClickButtonWatch(){
        clickListItem(R.id.rv_movies, numTestingPosition)
        try {
            Intents.init()
            val expectedIntent: Matcher<Intent> =allOf(hasAction(Intent.ACTION_VIEW), hasData(mockDataMovies.urlWatch))
            intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))
            clickOn(R.id.btn_watch)
            intended(expectedIntent)
        }finally {
            Intents.release()
        }
    }

    @Test
    fun navigateBackButton(){
        clickListItem(R.id.rv_movies, numTestingPosition)
        clickOn(R.id.btn_back)
        assertDisplayed(R.id.home_page_layout)
    }

    @Test
    fun checkLabelProductionOrNetwork(){
        clickListItem(R.id.rv_movies, numTestingPosition)
        assertDisplayed(R.id.tv_lable_companies,R.string.companies)
        clickOn(R.id.btn_back)
        onView(withId(R.id.tl_home)).perform(general.selectTabAtPosition(1))
        clickListItem(R.id.rv_tv_show, numTestingPosition)
        assertDisplayed(R.id.tv_lable_companies,R.string.network)

    }

    @Test
    @Throws(Exception::class)
    fun testInsertDataToFavoriteDb() = runBlocking{
        clickListItem(R.id.rv_movies, numTestingPosition)
        this@DetailsFeatures.favoriteDao.insert(mockDataMovies)
        val data = this@DetailsFeatures.favoriteDao.observeAllData()
        assertThat(data, contains(mockDataMovies))
    }

    @Test
    @Throws(Exception::class)
    fun testDeleteDataToFavoriteDb() = runBlocking{
        clickListItem(R.id.rv_movies, numTestingPosition)
        this@DetailsFeatures.favoriteDao.insert(mockDataMovies)
        this@DetailsFeatures.favoriteDao.delete(mockDataMovies)
        val data = this@DetailsFeatures.favoriteDao.observeAllData()
        assertThat(data, not(hasItem(data)))
    }

    @Test
    fun changeColorWhenClickBtnFavorite(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        clickListItem(R.id.rv_movies, numTestingPosition)
        onView(withId(R.id.fab_favorite)).check(matches(general.checkColorFab(ColorStateList.valueOf(
            ContextCompat.getColor(context,
                R.color.white)))))

        clickOn(R.id.fab_favorite)
        onView(withId(R.id.fab_favorite)).check(matches(general.checkColorFab(ColorStateList.valueOf(
            ContextCompat.getColor(context,
                R.color.pink)))))
    }
}

