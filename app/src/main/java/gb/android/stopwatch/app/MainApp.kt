package gb.android.stopwatch.app

import android.app.Application
import gb.android.stopwatch.di.application
import gb.android.stopwatch.di.mainScreen
import org.koin.core.context.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(application, mainScreen))
        }
    }

}