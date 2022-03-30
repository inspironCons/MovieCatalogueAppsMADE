package made.dicoding.moviecatalogueapps

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication:Application(){
    @Inject lateinit var networkFlipperPlugin:NetworkFlipperPlugin

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            //#issue flipper ketika menjalankan instrument testing agar di komen
            //SoLoader.init(this, false)
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.addPlugin(DatabasesFlipperPlugin(this))
            client.addPlugin(networkFlipperPlugin)
            client.start()
        }
    }

}