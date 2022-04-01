package made.dicoding.moviecatalogueapps.core.di

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import made.dicoding.moviecatalogueapps.core.common.ConstanNameHelper
import made.dicoding.moviecatalogueapps.core.data.remote.network.DetailMoviesApi
import made.dicoding.moviecatalogueapps.core.data.remote.network.TrendingApi
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val networkFlipperPlugin = NetworkFlipperPlugin()

    @Provides
    fun networkFlipperPlugin():NetworkFlipperPlugin = networkFlipperPlugin

    private val pinning = CertificatePinner.Builder()
        .add(ConstanNameHelper.HOST_URL,ConstanNameHelper.PIN_SHA256)
        .build()

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin))
        .certificatePinner(pinning)
        .build()

    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(ConstanNameHelper.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    fun movieApi(retrofit: Retrofit): TrendingApi = retrofit.create(TrendingApi::class.java)

    @Provides
    fun detailMoviesApi(retrofit: Retrofit): DetailMoviesApi = retrofit.create(DetailMoviesApi::class.java)
}