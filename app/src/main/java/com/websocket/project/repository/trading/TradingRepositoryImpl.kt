package com.websocket.project.repository.trading

import com.websocket.project.data.remote.trading.TradingClient
import com.websocket.project.request.authentication_request.AuthenticationRequest
import com.websocket.project.request.spot_request.SpotRequest
import com.websocket.project.response.spot_response.SpotResponse
import io.reactivex.Flowable
import javax.inject.Inject

class TradingRepositoryImpl @Inject constructor(
    private val tradingClient: TradingClient
) : TradingRepository {
    override fun subscribeSpot(
        authenticationRequest: AuthenticationRequest,
        spotRequest: SpotRequest
    ): Flowable<SpotResponse> {
        return tradingClient.subscribeSpot(authenticationRequest, spotRequest)
    }

    override fun unsubscribeSpot(spotRequest: SpotRequest) {
        tradingClient.unsubscribeSpot(spotRequest)
    }
}