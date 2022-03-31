package made.dicoding.moviecatalogueapps.core.common

import made.dicoding.moviecatalogueapps.core.BuildConfig

object ConstanNameHelper {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500"
    const val API_KEY = BuildConfig.API_KEY
    const val DB_NAME = "movie_db"
    const val DB_VERSION = 1
    const val LANGUAGE = "en-EN"
    const val REGIONS = "ID"

    const val MOVIES_TYPE = "MOVIES_TYPE"
    const val TV_TYPE = "TV_TYPE"
    val HEX_CHARS = "0123456789ABCDEF".toCharArray()
}