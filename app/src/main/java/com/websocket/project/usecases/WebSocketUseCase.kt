package com.websocket.project.usecases

import com.tradingview.lightweightcharts.api.series.models.BarData
import com.websocket.project.model.CryptoPairModel
import com.websocket.project.request.SubscribeTickerRequest
import com.websocket.project.request.TickerRequestParams
import com.websocket.project.repository.WebSocketRepository
import com.websocket.project.request.CandleRequestParams
import com.websocket.project.request.SubscribeCandleRequest
import io.reactivex.Flowable

class WebSocketUseCase(private val webSocketRepository: WebSocketRepository) {

    fun subscribeTickers(): Flowable<HashMap<String, CryptoPairModel>> {
        val subscribeTicker = SubscribeTickerRequest(
            method = "subscribe",
            ch = "ticker/3s/batch",
            params = TickerRequestParams(arrayListOf("*")),
            id = 123
        )
        return webSocketRepository.subscribeTicker(subscribeTicker)
    }

    fun unsubscribeTicker(){
        val unsubscribeTicker = SubscribeTickerRequest(
            method = "unsubscribe",
            ch = "ticker/3s/batch",
            params = TickerRequestParams(arrayListOf("*")),
            id = 123
        )
        webSocketRepository.unsubscribeTicker(unsubscribeTicker)
    }

    fun subscribeCandle(pairName: String): Flowable<List<BarData>> {
        val subscribeCandle = SubscribeCandleRequest(
            method = "subscribe",
            ch = "candles/M1",
            params = CandleRequestParams(arrayListOf(pairName), 50),
            id = 123
        )
        return webSocketRepository.subscribeCandle(subscribeCandle)
    }

    fun unsubscribeCandle(pairName: String) {
        val unsubscribeCandle = SubscribeCandleRequest(
            method = "unsubscribe",
            ch = "candles/M1",
            params = CandleRequestParams(arrayListOf(pairName), 50),
            id = 123
        )
        webSocketRepository.unsubscribeCandle(unsubscribeCandle)
    }
}