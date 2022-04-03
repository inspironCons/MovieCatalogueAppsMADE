package made.dicoding.moviecatalogueapps

import android.view.View
import android.widget.ProgressBar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaAssertions.assertAny
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickBack
import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItem
import made.dicoding.moviecatalogueapps.core.utils.EspressoIdling
import made.dicoding.moviecatalogueapps.core.utils.General
import made.dicoding.moviecatalogueapps.core.utils.General.toGetYear
import made.dicoding.moviecatalogueapps.utils.BaseUiTest
import made.dicoding.moviecatalogueapps.utils.GeneralTesting
import org.junit.Test
import org.junit.runner.RunWith
import made.dicoding.moviecatalogueapps.core.R as Core


@RunWith(AndroidJUnit4::class)
class HomeFeatures: BaseUiTest() {

    private val general = GeneralTesting()

    private val mockDataMovies = General.dummyMoviesList()
    private val mockDataTvShows = General.dummyTvShowsList()

    @Test
    fun showLoaderWhileFetchingTheListMovie() {
        IdlingRegistry.getInstance().unregister(EspressoIdling.getEspressoIdlingResource)
        assertAny<ProgressBar>(withId(R.id.load_content_movies),"Jika gagal coba test pada fungsi showLoaderWhileFetchingTheListMovie"){
            it.visibility == View.VISIBLE
        }
    }

    @Test
    fun showLoaderWhileFetchingTheListTvShows() {
        IdlingRegistry.getInstance().unregister(EspressoIdling.getEspressoIdlingResource)
        onView(withId(R.id.tl_home)).perform(general.selectTabAtPosition(1))
        assertAny<ProgressBar>(withId(R.id.load_content_tv_show),"Jika gagal coba test pada fungsi showLoaderWhileFetchingTheListTvShows"){
            it.visibility == View.VISIBLE
        }
    }

    @Test
    fun hideLoaderWhileFetchingTheMovieListDone() {
        assertNotDisplayed(R.id.load_content_movies)
    }


    @Test
    fun hideLoaderWhileFetchingTheTvShowsListDone() {
        onView(withId(R.id.tl_home)).perform(general.selectTabAtPosition(1))
        assertNotDisplayed(R.id.load_content_tv_show)
    }

    @Test
    fun displayMoviePlaylist(){
        onView(withId(R.id.tl_home)).perform(general.selectTabAtPosition(0))
        assertRecyclerViewItemCount(R.id.rv_movies,20)
        val years = mockDataMovies.releaseDate?.toGetYear()
        assertDisplayedAtPosition(
            R.id.rv_movies,moviePosition,
            Core.id.movie_title,
            "${mockDataMovies.title} ($years)")

        assertDisplayedAtPosition(R.id.rv_movies,moviePosition,Core.id.movie_score,"${mockDataMovies.voteAverage}%")
        assertDisplayedAtPosition(R.id.rv_movies,moviePosition,Core.id.movie_ori_language,mockDataMovies.originLanguage?.uppercase()?:"")

    }

    @Test
    fun displayTvShowsPlaylist(){
        onView(withId(R.id.tl_home)).perform(general.selectTabAtPosition(1))
        assertRecyclerViewItemCount(R.id.rv_tv_show,20)
        val years = mockDataTvShows.releaseDate?.toGetYear()
        assertDisplayedAtPosition(
            R.id.rv_tv_show,
            tvShowPosition,
            Core.id.movie_title,
            "${mockDataTvShows.title} ($years)")

        assertDisplayedAtPosition(R.id.rv_tv_show,tvShowPosition,Core.id.movie_score,"${mockDataTvShows.voteAverage}%")
        assertDisplayedAtPosition(R.id.rv_tv_show,tvShowPosition,Core.id.movie_ori_language,mockDataTvShows.originLanguage?.uppercase()?:"")
    }

    @Test
    fun navigateToDetailMovies(){
        clickListItem(R.id.rv_movies,moviePosition)
        assertDisplayed(R.id.detail_activity)
        clickBack()
        onView(withId(R.id.tl_home)).perform(general.selectTabAtPosition(1))
        clickListItem(R.id.rv_tv_show,tvShowPosition)
        assertDisplayed(R.id.detail_activity)
    }
}