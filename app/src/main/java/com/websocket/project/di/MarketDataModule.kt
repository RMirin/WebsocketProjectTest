package com.websocket.project.di

import com.websocket.project.data.remote.market_data.MarketDataClient
import com.websocket.project.repository.market_data.MarketDataRepository
import com.websocket.project.repository.market_data.MarketDataRepositoryImpl
import com.websocket.project.usecases.MarketDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MarketDataModule {

    @Provides
    @ViewModelScoped
    fun provideMarketDataUseCase(marketDataRepository: MarketDataRepository): MarketDataUseCase =
        MarketDataUseCase(marketDataRepository)

    @Provides
    @ViewModelScoped
    fun provideMarketDataRepository(marketDataClient: MarketDataClient): MarketDataRepository =
        MarketDataRepositoryImpl(marketDataClient)
}