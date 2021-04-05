package hu.vm.bme.skylordsauctions

import android.app.Application
import hu.vm.bme.skylordsauctions.dagger.ApplicationComponent
import hu.vm.bme.skylordsauctions.dagger.DaggerApplicationComponent

class SkylordsApplication: Application() {
    lateinit var injector: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injector = DaggerApplicationComponent.builder().build()
    }
}