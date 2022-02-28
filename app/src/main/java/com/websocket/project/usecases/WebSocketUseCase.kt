package com.websocket.project.usecases

import com.websocket.project.model.CryptoPairModel
import com.websocket.project.response.CryptoResponse
import com.websocket.project.request.SubscribeTickerRequest
import com.websocket.project.request.TickerRequestParams
import com.websocket.project.repository.WebSocketRepository
import io.reactivex.Flowable

class WebSocketUseCase(private val webSocketRepository: WebSocketRepository) {

    operator fun invoke(): Flowable<HashMap<String, CryptoPairModel>> {
        val subscribeTicker = SubscribeTickerRequest(
            method = "subscribe",
            ch = "ticker/3s/batch",
            params = TickerRequestParams(
                arrayListOf(
                    "AVEUSDT",
                    "ADAUSDT",
                    "AVAXUSDT",
                    "BCHUSDT",
                    "BNBUSDT",
                    "BTCUSDT",
                    "DOTUSDT",
                    "EOSUSDT",
                    "ETHUSDT",
                    "LTCUSDT",
                    "MANAUSDT",
                    "SHIBUSDT",
                    "TRXUSDT",
                    "UNIUSDT",
                    "XLMUSDT",
                    "XRPUSDT",
                    "ZECUSDT"
                )
            ),
            id = 123
        )
        return webSocketRepository.observeTicker(subscribeTicker)
    }
}