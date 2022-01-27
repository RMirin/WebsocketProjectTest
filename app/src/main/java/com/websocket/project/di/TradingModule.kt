package com.websocket.project.di

import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.websocket.project.data.remote.trading.TradingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(FragmentComponent::class)
object TradingModule {

    @Provides
    fun provideTradingApi(okHttpClient: OkHttpClient, lifecycle: Lifecycle): TradingApi =
        Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory(TradingApi.BASE_TRADING_URI))
            .lifecycle(lifecycle)
            .addMessageAdapterFactory(GsonMessageAdapter.Factory())
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .build()
            .create()
}