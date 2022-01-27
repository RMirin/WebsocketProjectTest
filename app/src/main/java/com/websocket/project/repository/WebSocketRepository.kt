package com.websocket.project.repository

import com.tradingview.lightweightcharts.api.series.models.BarData
import com.websocket.project.model.CryptoPairModel
import com.websocket.project.request.SubscribeCandleRequest
import com.websocket.project.response.CryptoResponse
import com.websocket.project.request.SubscribeTickerRequest
import io.reactivex.Flowable

interface WebSocketRepository {
    fun observeTicker(subscribeTickerRequest: SubscribeTickerRequest): Flowable<HashMap<String, CryptoPairModel>>
    fun observeCandle(subscribeCandleRequest: SubscribeCandleRequest): Flowable<List<BarData>>
}