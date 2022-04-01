package made.dicoding.moviecatalogueapps

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary2.FlipperLeakListener
import com.facebook.flipper.plugins.leakcanary2.LeakCanary2FlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import dagger.hilt.android.HiltAndroidApp
import leakcanary.LeakCanary
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication:Application(){
    @Inject lateinit var networkFlipperPlugin:NetworkFlipperPlugin

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            SoLoader.init(this, false) 
            /*
                set the flipper listener in leak canary config
            */
            LeakCanary.config = LeakCanary.config.copy(
                onHeapAnalyzedListener = FlipperLeakListener()
            )
            if(FlipperUtils.shouldEnableFlipper(this)){
                //#issue flipper ketika menjalankan instrument testing agar di komen
                val client = AndroidFlipperClient.getInstance(this)
                client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
                client.addPlugin(DatabasesFlipperPlugin(this))
                client.addPlugin(networkFlipperPlugin)
                client.addPlugin(LeakCanary2FlipperPlugin())
                client.start()
            }

        }
    }

}