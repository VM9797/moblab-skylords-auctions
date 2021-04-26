package hu.vm.bme.skylordsauctions.network

import dagger.Module
import dagger.Provides
import hu.vm.bme.skylordsauctions.network.cardbase.CardbaseApi
import hu.vm.bme.skylordsauctions.network.smj.SmjApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideSmjApi(client: OkHttpClient): SmjApi {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(NetworkConfig.SMJ_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SmjApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCardbaseApi(client: OkHttpClient): CardbaseApi {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(NetworkConfig.CARDBASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CardbaseApi::class.java)
    }
}