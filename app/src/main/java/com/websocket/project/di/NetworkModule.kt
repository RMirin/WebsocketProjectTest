package com.websocket.project.di

import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.websocket.project.data.remote.market_data.MarketDataApi
import com.websocket.project.data.remote.market_data.MarketDataClient
import com.websocket.project.data.remote.market_data.MarketDataClientImpl
import com.websocket.project.data.remote.trading.TradingApi
import com.websocket.project.data.remote.trading.TradingClient
import com.websocket.project.data.remote.trading.TradingClientImpl
import com.websocket.project.interceptor.NetworkConnectionInterceptor
import com.websocket.project.service.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
object NetworkModule {

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
    fun provideMarketDataApi(okHttpClient: OkHttpClient, lifecycle: Lifecycle): MarketDataApi =
        Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory(MarketDataApi.BASE_MARKET_DATA_URI))
            .lifecycle(lifecycle)
            .addMessageAdapterFactory(GsonMessageAdapter.Factory())
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .build()
            .create()

    @Provides
    @Singleton
    fun provideMarketDataClient(marketDataApi: MarketDataApi): MarketDataClient =
        MarketDataClientImpl(marketDataApi)

    @Provides
    @Singleton
    fun provideTradingApi(okHttpClient: OkHttpClient, lifecycle: Lifecycle): TradingApi =
        Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory(TradingApi.BASE_TRADING_URI))
            .lifecycle(lifecycle)
            .addMessageAdapterFactory(GsonMessageAdapter.Factory())
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .build()
            .create()

    @Provides
    @Singleton
    fun provideTradingClient(tradingApi: TradingApi): TradingClient =
        TradingClientImpl(tradingApi)
}