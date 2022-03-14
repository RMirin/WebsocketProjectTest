package com.websocket.project.data.remote

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import com.websocket.project.request.SubscribeTickerRequest
import com.websocket.project.response.CryptoResponse
import io.reactivex.Flowable

interface HitBtcApi {

    @Receive
    fun openWebSocketEvent(): Flowable<WebSocket.Event>

    @Receive
    fun receiveResponse(): Flowable<String>

    @Send
    fun sendTickerRequest(subscribeTickerRequest: SubscribeTickerRequest)

    @Receive
    fun observeTicker(): Flowable<CryptoResponse>

    companion object {
        const val BASE_URI = "wss://api.multiexchange.com/api/3/ws/public"
    }
}