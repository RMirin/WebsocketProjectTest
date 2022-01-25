package com.websocket.project.data.remote

import com.websocket.project.request.SubscribeCandleRequest
import com.websocket.project.response.CryptoResponse
import com.websocket.project.request.SubscribeTickerRequest
import com.websocket.project.response.candle_response.CandleResponse
import io.reactivex.Flowable

interface HitBtcClient {
    fun subscribeTicker(subscribeTickerRequest: SubscribeTickerRequest): Flowable<CryptoResponse>
    fun subscribeCandle(subscribeCandleRequest: SubscribeCandleRequest): Flowable<CandleResponse>
    fun unsubscribeTicker(subscribeTickerRequest: SubscribeTickerRequest)
    fun unsubscribeCandle(subscribeCandleRequest: SubscribeCandleRequest)
}