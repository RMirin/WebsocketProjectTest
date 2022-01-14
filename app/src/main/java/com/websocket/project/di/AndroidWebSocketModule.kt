package com.websocket.project.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.websocket.project.data.remote.HitBtcApi
import com.websocket.project.data.remote.HitBtcClientImpl
import com.websocket.project.repository.WebSocketRepository
import com.websocket.project.repository.WebSocketRepositoryImpl
import com.websocket.project.service.NetworkService
import com.websocket.project.service.NetworkServiceImpl
import com.websocket.project.usecases.WebSocketUseCase
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
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
object AndroidWebSocketModule {

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

    @Provides
    @Singleton
    fun provideOkHttpClient(networkService: NetworkService): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addInterceptor(NetworkConnectionInterceptor(networkService))
            .also {
                val trustManagerFactory: TrustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(null as KeyStore?)
                val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers
                check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                    "Unexpected default trust managers:" + trustManagers.contentToString()
                }
            }
            .build()

    @Provides
    @Singleton
    fun provideScarlet(okHttpClient: OkHttpClient, lifecycle: Lifecycle): HitBtcApi =
        Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory(HitBtcApi.BASE_URI))
            .lifecycle(lifecycle)
            .addMessageAdapterFactory(GsonMessageAdapter.Factory())
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .build()
            .create()

    @Provides
    @Singleton
    fun provideHitBtcClientImpl(
        hitBtcApi: HitBtcApi
    ): HitBtcClientImpl =
        HitBtcClientImpl(hitBtcApi)

    @Provides
    @Singleton
    fun provideWebSocketRepository(
        hitBtcClientImpl: HitBtcClientImpl,
    ): WebSocketRepository =
        WebSocketRepositoryImpl(hitBtcClientImpl)

    @Provides
    @Singleton
    fun provideWebSocketUseCase(webSocketRepositoryImpl: WebSocketRepositoryImpl): WebSocketUseCase =
        WebSocketUseCase(webSocketRepositoryImpl)
}