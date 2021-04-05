package hu.vm.bme.skylordsauctions.util

import android.app.Activity
import hu.vm.bme.skylordsauctions.SkylordsApplication
import hu.vm.bme.skylordsauctions.dagger.ApplicationComponent

val Activity.injector: ApplicationComponent
    get() = (application as SkylordsApplication).injector