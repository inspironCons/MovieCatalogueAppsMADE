package made.dicoding.moviecatalogueapps.favorite.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import made.dicoding.moviecatalogueapps.core.di.FavoriteModuleDependencies
import made.dicoding.moviecatalogueapps.favorite.presentation.favorite.FavoriteActivity

@Component(dependencies = [
    FavoriteModuleDependencies::class
])
interface FavoriteComponent {
    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(mapsModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}