package com.websocket.project.data.remote.trading

import javax.inject.Inject

class TradingClientImpl @Inject constructor(
    private val tradingApi: TradingApi
) : TradingClient {
}