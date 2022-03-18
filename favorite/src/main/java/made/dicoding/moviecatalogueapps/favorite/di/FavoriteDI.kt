package made.dicoding.moviecatalogueapps.favorite.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import made.dicoding.moviecatalogueapps.di.AppComponent
import made.dicoding.moviecatalogueapps.favorite.presentation.favorite.movies.MoviesFavoriteFragment
import made.dicoding.moviecatalogueapps.favorite.presentation.favorite.tvshows.TvShowsFavoriteFragment
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class FavoriteFeatureScope

@FavoriteFeatureScope
@Component(dependencies = [AppComponent::class])
interface FavoriteDI{
    fun inject(fragment:MoviesFavoriteFragment)
    fun inject(fragment:TvShowsFavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(mapsModuleDependencies: AppComponent): Builder
        fun build(): FavoriteDI
    }
}

