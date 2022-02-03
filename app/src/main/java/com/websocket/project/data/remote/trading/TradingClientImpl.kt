package com.websocket.project.data.remote.trading

import android.annotation.SuppressLint
import com.tinder.scarlet.WebSocket
import com.websocket.project.request.authentication_request.AuthenticationRequest
import com.websocket.project.request.spot_request.SpotRequest
import com.websocket.project.response.spot_response.SpotResponse
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TradingClientImpl @Inject constructor(
    private val tradingApi: TradingApi
) : TradingClient {
    @SuppressLint("CheckResult")
    override fun subscribeSpot(
        authenticationRequest: AuthenticationRequest,
        spotRequest: SpotRequest
    ): Flowable<SpotResponse> {
        tradingApi.openWebSocketEvent()
            .filter { it is WebSocket.Event.OnConnectionOpened<*> }
            .subscribe {
                tradingApi.sendAuthenticationRequest(authenticationRequest)
            }
        tradingApi.observeAuthenticationResponse()
            .filter {
                it.result
            }
            .subscribe {
                tradingApi.sendSpotRequest(spotRequest)
            }
        return tradingApi.observeSpot().subscribeOn(Schedulers.io())

    }

    override fun unsubscribeSpot(spotRequest: SpotRequest) {
        tradingApi.sendUnsubscribeSpotRequest(spotRequest)
    }
}