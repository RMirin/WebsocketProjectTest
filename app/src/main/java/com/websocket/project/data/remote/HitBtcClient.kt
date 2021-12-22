package com.websocket.project.data.remote

import com.websocket.project.response.CryptoResponse
import com.websocket.project.request.SubscribeTickerRequest
import io.reactivex.Flowable

interface HitBtcClient {

    fun subscribeTicker(subscribeTickerRequest: SubscribeTickerRequest): Flowable<CryptoResponse>
}