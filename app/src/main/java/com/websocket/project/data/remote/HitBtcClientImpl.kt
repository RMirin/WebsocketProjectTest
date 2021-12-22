package com.websocket.project.data.remote

import android.annotation.SuppressLint
import com.websocket.project.request.SubscribeTickerRequest
import com.websocket.project.response.CryptoResponse
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Singleton
class HitBtcClientImpl(private val hitBtcApi: HitBtcApi) : HitBtcClient {

    @SuppressLint("CheckResult")
    override fun subscribeTicker(subscribeTickerRequest: SubscribeTickerRequest): Flowable<CryptoResponse> {
        hitBtcApi.openWebSocketEvent()
            .filter {
                true
            }
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
}