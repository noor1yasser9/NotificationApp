package com.nurbk.ps.notificationapp.di


import com.nurbk.ps.notificationapp.network.NotificationInterface
import com.nurbk.ps.notificationapp.other.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun InstaceRetrofit(baseUrl: String) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .apply {
                val builder = OkHttpClient.Builder()
                builder.connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                    .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                    .readTimeout(5, TimeUnit.MINUTES) // read timeout
                this.client(builder.build())
            }
            .build()


    @Provides
    @Singleton
    fun notificationInterface() =
        InstaceRetrofit(BASE_URL).create(NotificationInterface::class.java)

}