package com.websocket.project.usecases

import com.tradingview.lightweightcharts.api.series.models.BarData
import com.websocket.project.model.CryptoPairModel
import com.websocket.project.request.ticker_request.SubscribeTickerRequest
import com.websocket.project.request.ticker_request.TickerRequestParams
import com.websocket.project.repository.market_data.MarketDataRepository
import com.websocket.project.request.candle_request.CandleRequestParams
import com.websocket.project.request.candle_request.SubscribeCandleRequest
import io.reactivex.Flowable
import javax.inject.Inject

class MarketDataUseCase @Inject constructor(private val marketDataRepository: MarketDataRepository) {

    fun subscribeTickers(): Flowable<HashMap<String, CryptoPairModel>> {
        val subscribeTicker = SubscribeTickerRequest(
            method = "subscribe",
            ch = "ticker/3s/batch",
            params = TickerRequestParams(arrayListOf("*")),
            id = 123
        )
        return marketDataRepository.subscribeTicker(subscribeTicker)
    }

    fun unsubscribeTicker(){
        val unsubscribeTicker = SubscribeTickerRequest(
            method = "unsubscribe",
            ch = "ticker/3s/batch",
            params = TickerRequestParams(arrayListOf("*")),
            id = 123
        )
        marketDataRepository.unsubscribeTicker(unsubscribeTicker)
    }

    fun subscribeCandle(pairName: String): Flowable<List<BarData>> {
        val subscribeCandle = SubscribeCandleRequest(
            method = "subscribe",
            ch = "candles/M1",
            params = CandleRequestParams(arrayListOf(pairName), 50),
            id = 123
        )
        return marketDataRepository.subscribeCandle(subscribeCandle)
    }

    fun unsubscribeCandle(pairName: String) {
        val unsubscribeCandle = SubscribeCandleRequest(
            method = "unsubscribe",
            ch = "candles/M1",
            params = CandleRequestParams(arrayListOf(pairName), 50),
            id = 123
        )
        marketDataRepository.unsubscribeCandle(unsubscribeCandle)
    }
}