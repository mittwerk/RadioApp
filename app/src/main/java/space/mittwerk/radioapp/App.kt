package space.mittwerk.radioapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import space.mittwerk.radioapp.init.LoggerInitializer
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var loggerInitializer: LoggerInitializer

    override fun onCreate() {
        super.onCreate()
        // initialize timber trees for client side logging
        loggerInitializer.initialize()
    }
}
