package com.websocket.project.repository.trading

import com.websocket.project.request.authentication_request.AuthenticationRequest
import com.websocket.project.request.spot_request.SpotRequest
import com.websocket.project.response.spot_response.SpotResponse
import io.reactivex.Flowable

interface TradingRepository {
    fun subscribeSpot(authenticationRequest: AuthenticationRequest, spotRequest: SpotRequest): Flowable<SpotResponse>
    fun unsubscribeSpot(spotRequest: SpotRequest)
}