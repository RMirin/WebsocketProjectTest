package com.websocket.project.data.remote.market_data

import android.annotation.SuppressLint
import android.util.Log
import com.tinder.scarlet.WebSocket
import com.websocket.project.request.SubscribeCandleRequest
import com.websocket.project.request.SubscribeTickerRequest
import com.websocket.project.response.CryptoResponse
import com.websocket.project.response.candle_response.CandleResponse
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

class MarketDataClientImpl(
    private val marketDataApi: MarketDataApi
) : MarketDataClient {

    private var connectionOpen = false

    @SuppressLint("CheckResult")
    override fun subscribeTicker(subscribeTickerRequest: SubscribeTickerRequest): Flowable<CryptoResponse> {
        if (connectionOpen) {
            marketDataApi.sendTickerRequest(subscribeTickerRequest)
        } else {
            marketDataApi.openWebSocketEvent()
                .filter { it is WebSocket.Event.OnConnectionOpened<*> }
                .subscribe {
                    connectionOpen = true
                    marketDataApi.sendTickerRequest(subscribeTickerRequest)
                }
        }

        return marketDataApi.observeTicker()
            .subscribeOn(Schedulers.io())
            .filter { cryptoResponse ->
                Log.d("TAG", "subscribeTicker: ")
                cryptoResponse.ch != null
            }
    }

    @SuppressLint("CheckResult")
    override fun unsubscribeTicker(subscribeTickerRequest: SubscribeTickerRequest) {
        marketDataApi.sendUnsubscribeTickerRequest(subscribeTickerRequest)
        Log.d("TAG", "unsubscribeTicker: ")
    }

    @SuppressLint("CheckResult")
    override fun subscribeCandle(subscribeCandleRequest: SubscribeCandleRequest): Flowable<CandleResponse> {
        if (connectionOpen) {
            marketDataApi.sendCandleRequest(subscribeCandleRequest)
        } else {
            marketDataApi.openWebSocketEvent()
                .filter { it is WebSocket.Event.OnConnectionOpened<*> }
                .subscribe {
                    connectionOpen = true
                    marketDataApi.sendCandleRequest(subscribeCandleRequest)
                }
        }

        return marketDataApi.observeCandle()
            .subscribeOn(Schedulers.io())
            .filter { cryptoResponse ->
                Log.d("TAG", "subscribeCandle: $cryptoResponse")
                cryptoResponse.update != null || cryptoResponse.snapshot != null
            }
    }

    @SuppressLint("CheckResult")
    override fun unsubscribeCandle(subscribeCandleRequest: SubscribeCandleRequest) {
        marketDataApi.sendUnsubscribeCandleRequest(subscribeCandleRequest)
    }
}