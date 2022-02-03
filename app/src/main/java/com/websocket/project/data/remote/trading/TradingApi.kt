package com.websocket.project.data.remote.trading

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import com.websocket.project.request.authentication_request.AuthenticationRequest
import com.websocket.project.request.spot_request.SpotRequest
import com.websocket.project.response.authentication_response.AuthenticationResponse
import com.websocket.project.response.spot_response.SpotResponse
import io.reactivex.Flowable

interface TradingApi {

    @Receive
    fun openWebSocketEvent(): Flowable<WebSocket.Event>

    @Send
    fun sendAuthenticationRequest(authenticationRequest: AuthenticationRequest)

    @Receive
    fun observeAuthenticationResponse(): Flowable<AuthenticationResponse>

    @Send
    fun sendSpotRequest(spotRequest: SpotRequest)

    @Send
    fun sendUnsubscribeSpotRequest(spotRequest: SpotRequest)

    @Receive
    fun observeSpot(): Flowable<SpotResponse>

    companion object {
        const val BASE_TRADING_URI = "wss://api.app.exex.com/api/3/ws/trading"
    }
}