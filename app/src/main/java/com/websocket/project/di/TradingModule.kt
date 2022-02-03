package com.websocket.project.di

import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.websocket.project.data.remote.market_data.MarketDataClient
import com.websocket.project.data.remote.trading.TradingApi
import com.websocket.project.data.remote.trading.TradingClient
import com.websocket.project.repository.market_data.MarketDataRepository
import com.websocket.project.repository.market_data.MarketDataRepositoryImpl
import com.websocket.project.repository.trading.TradingRepository
import com.websocket.project.repository.trading.TradingRepositoryImpl
import com.websocket.project.usecases.MarketDataUseCase
import com.websocket.project.usecases.TradingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object TradingModule {
    @Provides
    @ViewModelScoped
    fun provideTradingUseCase(tradingRepository: TradingRepository): TradingUseCase =
        TradingUseCase(tradingRepository)

    @Provides
    @ViewModelScoped
    fun provideTradingRepository(tradingClient: TradingClient): TradingRepository =
        TradingRepositoryImpl(tradingClient)
}