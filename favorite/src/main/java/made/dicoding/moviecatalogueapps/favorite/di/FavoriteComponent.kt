package made.dicoding.moviecatalogueapps.favorite.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import made.dicoding.moviecatalogueapps.di.FavoriteModuleDepedencies
import made.dicoding.moviecatalogueapps.favorite.presentation.favorite.FavoriteActivity

@Component(dependencies = [
    FavoriteModuleDepedencies::class
])
interface FavoriteComponent {
    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(mapsModuleDependencies: FavoriteModuleDepedencies): Builder
        fun build(): FavoriteComponent
    }
}