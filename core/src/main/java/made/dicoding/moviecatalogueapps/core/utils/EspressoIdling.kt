@file:Suppress("unused", "unused")

package made.dicoding.moviecatalogueapps.core.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdling {
    private const val RESOURCE: String = "GLOBAL"
    private val espressoTestIdlingResource = CountingIdlingResource(RESOURCE)
    val getEspressoIdlingResource get() = espressoTestIdlingResource

    fun increment() {
        espressoTestIdlingResource.increment()
    }

    fun decrement() {
        espressoTestIdlingResource.decrement()
    }

}