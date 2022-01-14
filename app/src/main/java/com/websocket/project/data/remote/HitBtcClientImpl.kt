package com.websocket.project.data.remote

import android.annotation.SuppressLint
import android.util.Log
import com.websocket.project.request.SubscribeCandleRequest
import com.websocket.project.request.SubscribeTickerRequest
import com.websocket.project.response.CryptoResponse
import com.websocket.project.response.candle_response.CandleResponse
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Singleton
class HitBtcClientImpl(
    private val hitBtcApi: HitBtcApi
) : HitBtcClient {

    @SuppressLint("CheckResult")
    override fun subscribeTicker(subscribeTickerRequest: SubscribeTickerRequest): Flowable<CryptoResponse> {
        hitBtcApi.openWebSocketEvent()
            .subscribe({
                hitBtcApi.sendTickerRequest(subscribeTickerRequest)
            }, { e ->
                e.printStackTrace()
            })

        return hitBtcApi.observeTicker()
            .subscribeOn(Schedulers.io())
            .filter { cryptoResponse ->
                cryptoResponse.ch != null
            }
    }

    @SuppressLint("CheckResult")
    override fun subscribeCandle(subscribeCandleRequest: SubscribeCandleRequest): Flowable<CandleResponse> {
        hitBtcApi.openWebSocketEvent()
            .subscribe({
                hitBtcApi.sendCandleRequest(subscribeCandleRequest)
            }, { e ->
                e.printStackTrace()
            })

        return hitBtcApi.observeCandle()
            .subscribeOn(Schedulers.io())
            .filter { cryptoResponse ->
                Log.d("TAG", "subscribeCandle: $cryptoResponse")
                cryptoResponse.update != null || cryptoResponse.snapshot != null
            }
    }
}