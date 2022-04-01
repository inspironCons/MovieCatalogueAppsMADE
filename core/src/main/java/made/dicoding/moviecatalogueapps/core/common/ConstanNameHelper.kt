package made.dicoding.moviecatalogueapps.core.common

import made.dicoding.moviecatalogueapps.core.BuildConfig

object ConstanNameHelper {
    const val HOST_URL = BuildConfig.HOST_NAME
    const val BASE_URL = BuildConfig.BASE_URL
    const val PIN_SHA256 = BuildConfig.PIN_SHA256
    const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500"
    const val API_KEY = BuildConfig.API_KEY
    const val DB_NAME = BuildConfig.DB_NAME
    const val DB_VERSION = 1
    const val LANGUAGE = "en-EN"
    const val REGIONS = "ID"

    const val MOVIES_TYPE = "MOVIES_TYPE"
    const val TV_TYPE = "TV_TYPE"
}