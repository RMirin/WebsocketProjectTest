package com.websocket.project.repository.market_data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.tradingview.lightweightcharts.api.series.models.BarData
import com.websocket.project.converter.mapCandleToBarData
import com.websocket.project.converter.mapToCryptoPairModel
import com.websocket.project.data.remote.market_data.MarketDataClient
import com.websocket.project.model.CryptoPairModel
import com.websocket.project.request.SubscribeCandleRequest
import com.websocket.project.request.SubscribeTickerRequest
import io.reactivex.Flowable
import javax.inject.Inject

class MarketDataRepositoryImpl @Inject constructor(
    private val marketDataClient: MarketDataClient
) : MarketDataRepository {

    override fun subscribeTicker(subscribeTickerRequest: SubscribeTickerRequest): Flowable<HashMap<String, CryptoPairModel>> {
        return marketDataClient.subscribeTicker(subscribeTickerRequest)
            .map { cryptoResponse ->
                Log.d("TAG", "subscribeRepositoryTicker: $cryptoResponse")
                mapToCryptoPairModel(cryptoResponse.cryptoResponseData)
            }
    }

    override fun unsubscribeTicker(subscribeTickerRequest: SubscribeTickerRequest) {
        marketDataClient.unsubscribeTicker(subscribeTickerRequest)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun subscribeCandle(subscribeCandleRequest: SubscribeCandleRequest): Flowable<List<BarData>> {
        return marketDataClient.subscribeCandle(subscribeCandleRequest)
            .map { candleResponse ->
                Log.d("TAG", "subscribeRepositoryCandle: $candleResponse")
                if (candleResponse.update != null) {
                    mapCandleToBarData(candleResponse.update)
                } else {
                    mapCandleToBarData(candleResponse.snapshot)
                }
            }
    }

    override fun unsubscribeCandle(subscribeCandleRequest: SubscribeCandleRequest) {
        marketDataClient.unsubscribeCandle(subscribeCandleRequest)
    }

}