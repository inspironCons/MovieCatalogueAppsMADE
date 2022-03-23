package made.dicoding.moviecatalogueapps

import android.app.Application
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication:Application(){
    @Inject lateinit var networkFlipperPlugin:NetworkFlipperPlugin

    override fun onCreate() {
        super.onCreate()
//        #issue flipper ketika menjalankan instrument testing agar di komen
//        SoLoader.init(this, false)
//
//        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
//            val client = AndroidFlipperClient.getInstance(this)
//            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
//            client.addPlugin(DatabasesFlipperPlugin(this))
//            client.addPlugin(networkFlipperPlugin)
//            client.start()
//        }
    }

}