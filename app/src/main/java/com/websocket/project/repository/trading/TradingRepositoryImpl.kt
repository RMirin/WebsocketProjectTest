package com.websocket.project.repository.trading

import com.websocket.project.data.remote.trading.TradingClient
import javax.inject.Inject

class TradingRepositoryImpl @Inject constructor(
    private val tradingClient: TradingClient
) : TradingRepository {
}