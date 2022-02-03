package com.websocket.project.data.remote.market_data

import com.websocket.project.request.candle_request.SubscribeCandleRequest
import com.websocket.project.response.CryptoResponse
import com.websocket.project.request.ticker_request.SubscribeTickerRequest
import com.websocket.project.response.candle_response.CandleResponse
import io.reactivex.Flowable

interface MarketDataClient {
    fun subscribeTicker(subscribeTickerRequest: SubscribeTickerRequest): Flowable<CryptoResponse>
    fun subscribeCandle(subscribeCandleRequest: SubscribeCandleRequest): Flowable<CandleResponse>
    fun unsubscribeTicker(subscribeTickerRequest: SubscribeTickerRequest)
    fun unsubscribeCandle(subscribeCandleRequest: SubscribeCandleRequest)
}