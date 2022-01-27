package com.websocket.project.di

import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.websocket.project.data.remote.market_data.MarketDataApi
import com.websocket.project.data.remote.market_data.MarketDataClient
import com.websocket.project.data.remote.market_data.MarketDataClientImpl
import com.websocket.project.repository.MarketDataRepository
import com.websocket.project.repository.MarketDataRepositoryImpl
import com.websocket.project.usecases.MarketDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(ViewModelComponent::class)
object MarketDataModule {

    @Provides
    fun provideMarketDataUseCase(marketDataRepository: MarketDataRepository): MarketDataUseCase =
        MarketDataUseCase(marketDataRepository)

    @Provides
    fun provideMarketDataRepository(marketDataClient: MarketDataClient): MarketDataRepository =
        MarketDataRepositoryImpl(marketDataClient)

    @Provides
    fun provideMarketDataClient(marketDataApi: MarketDataApi): MarketDataClient =
        MarketDataClientImpl(marketDataApi)

    @Provides
    fun provideMarketDataApi(okHttpClient: OkHttpClient, lifecycle: Lifecycle): MarketDataApi =
        Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory(MarketDataApi.BASE_MARKET_DATA_URI))
            .lifecycle(lifecycle)
            .addMessageAdapterFactory(GsonMessageAdapter.Factory())
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .build()
            .create()
}