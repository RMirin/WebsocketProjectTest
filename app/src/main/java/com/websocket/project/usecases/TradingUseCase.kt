package com.websocket.project.usecases

import com.websocket.project.repository.trading.TradingRepository
import javax.inject.Inject

class TradingUseCase @Inject constructor(
    private val tradingRepository: TradingRepository
) {
}