package com.websocket.project.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.websocket.project.service.NetworkService
import com.websocket.project.service.NetworkServiceImpl
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.websocket.project.interceptor.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.KeyStore
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLifecycle(application: Application): Lifecycle =
        AndroidLifecycle.ofApplicationForeground(application)

    @Provides
    @Singleton
    fun provideNetworkService(@ApplicationContext context: Context): NetworkService =
        NetworkServiceImpl(
            context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
        )
}