package com.websocket.project.data.remote

import com.websocket.project.request.SubscribeTickerRequest
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import com.websocket.project.request.SubscribeCandleRequest
import com.websocket.project.response.CryptoResponse
import com.websocket.project.response.candle_response.CandleResponse
import io.reactivex.Flowable

interface HitBtcApi {

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
        const val BASE_URI = "wss://api.multiexchange.com/api/3/ws/public"
    }
}