package com.websocket.project.usecases

import com.websocket.project.repository.trading.TradingRepository
import com.websocket.project.request.authentication_request.AuthenticationRequest
import com.websocket.project.request.spot_request.SpotRequest
import com.websocket.project.response.spot_response.SpotResponse
import io.reactivex.Flowable
import javax.inject.Inject

class TradingUseCase @Inject constructor(
    private val tradingRepository: TradingRepository
) {
    fun subscribeSpot(): Flowable<SpotResponse> {
        val authenticationRequest = AuthenticationRequest()
        return tradingRepository.subscribeSpot(
            AuthenticationRequest(), SpotRequest()
        )
    }

    fun unsubscribeSpot() {
        tradingRepository.unsubscribeSpot(SpotRequest(method = "spot_unsubscribe"))
    }
}