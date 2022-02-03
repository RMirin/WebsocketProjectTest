package com.websocket.project.data.remote.market_data

import com.websocket.project.request.ticker_request.SubscribeTickerRequest
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import com.websocket.project.request.candle_request.SubscribeCandleRequest
import com.websocket.project.response.CryptoResponse
import com.websocket.project.response.candle_response.CandleResponse
import io.reactivex.Flowable

interface MarketDataApi {

    @Receive
    fun openWebSocketEvent(): Flowable<WebSocket.Event>

    @Send
    fun sendTickerRequest(subscribeTickerRequest: SubscribeTickerRequest)

    @Send
    fun sendUnsubscribeTickerRequest(subscribeTickerRequest: SubscribeTickerRequest)

    @Send
    fun sendCandleRequest(subscribeCandleRequest: SubscribeCandleRequest)

    @Send
    fun sendUnsubscribeCandleRequest(subscribeCandleRequest: SubscribeCandleRequest)

    @Receive
    fun observeTicker(): Flowable<CryptoResponse>

    @Receive
    fun observeCandle(): Flowable<CandleResponse>

    companion object {
        const val BASE_MARKET_DATA_URI = "wss://api.app.exex.com/api/3/ws/public"
    }
}