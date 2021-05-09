package hu.vm.bme.skylordsauctions.data

import dagger.Module
import dagger.Provides
import hu.vm.bme.skylordsauctions.SkylordsApplication
import javax.inject.Singleton

@Module
class DataModule(private val application: SkylordsApplication) {

    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase {
      return AppDatabase.getInstance(application)
    }

    @Provides
    @Singleton
    fun provideAppDaoProvider(appDatabase: AppDatabase): AppDaoProvider {
        return appDatabase
    }
}