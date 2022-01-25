package com.websocket.project.data.remote

import android.annotation.SuppressLint
import android.util.Log
import com.tinder.scarlet.ShutdownReason
import com.tinder.scarlet.WebSocket
import com.websocket.project.request.SubscribeCandleRequest
import com.websocket.project.request.SubscribeTickerRequest
import com.websocket.project.response.CryptoResponse
import com.websocket.project.response.candle_response.CandleResponse
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton
import kotlin.math.log

@Singleton
class HitBtcClientImpl(
    private val hitBtcApi: HitBtcApi
) : HitBtcClient {

    private var connectionOpen = false

    @SuppressLint("CheckResult")
    override fun subscribeTicker(subscribeTickerRequest: SubscribeTickerRequest): Flowable<CryptoResponse> {
        if (connectionOpen) {
            hitBtcApi.sendTickerRequest(subscribeTickerRequest)
        } else {
            hitBtcApi.openWebSocketEvent()
                .filter { it is WebSocket.Event.OnConnectionOpened<*> }
                .subscribe {
                    connectionOpen = true
                    hitBtcApi.sendTickerRequest(subscribeTickerRequest)
                }
        }

        return hitBtcApi.observeTicker()
            .subscribeOn(Schedulers.io())
            .filter { cryptoResponse ->
                Log.d("TAG", "subscribeTicker: ")
                cryptoResponse.ch != null
            }
    }

    @SuppressLint("CheckResult")
    override fun unsubscribeTicker(subscribeTickerRequest: SubscribeTickerRequest) {
        hitBtcApi.sendUnsubscribeTickerRequest(subscribeTickerRequest)
        Log.d("TAG", "unsubscribeTicker: ")
    }

    @SuppressLint("CheckResult")
    override fun subscribeCandle(subscribeCandleRequest: SubscribeCandleRequest): Flowable<CandleResponse> {
        if (connectionOpen)
            hitBtcApi.sendCandleRequest(subscribeCandleRequest)

        return hitBtcApi.observeCandle()
            .subscribeOn(Schedulers.io())
            .filter { cryptoResponse ->
                Log.d("TAG", "subscribeCandle: $cryptoResponse")
                cryptoResponse.update != null || cryptoResponse.snapshot != null
            }
    }

    @SuppressLint("CheckResult")
    override fun unsubscribeCandle(subscribeCandleRequest: SubscribeCandleRequest) {
        hitBtcApi.sendUnsubscribeCandleRequest(subscribeCandleRequest)
    }
}