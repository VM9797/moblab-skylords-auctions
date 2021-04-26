package hu.vm.bme.skylordsauctions

import android.app.Application
import hu.vm.bme.skylordsauctions.dagger.ApplicationComponent
import hu.vm.bme.skylordsauctions.dagger.DaggerApplicationComponent
import hu.vm.bme.skylordsauctions.data.DataModule

class SkylordsApplication: Application() {
    lateinit var injector: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injector = DaggerApplicationComponent.builder()
            .dataModule(DataModule(this))
            .build()
    }
}