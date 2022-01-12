package com.websocket.project.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.tradingview.lightweightcharts.api.series.common.SeriesData
import com.tradingview.lightweightcharts.api.series.models.BarData
import com.websocket.project.converter.mapToBarData
import com.websocket.project.converter.mapToCryptoPairModel
import com.websocket.project.data.remote.HitBtcClientImpl
import com.websocket.project.model.CryptoPairModel
import com.websocket.project.request.SubscribeCandleRequest
import com.websocket.project.request.SubscribeTickerRequest
import io.reactivex.Flowable
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketRepositoryImpl @Inject constructor(
    private val hitBtcClientImpl: HitBtcClientImpl
) : WebSocketRepository {

    override fun observeTicker(subscribeTickerRequest: SubscribeTickerRequest): Flowable<HashMap<String, CryptoPairModel>> {
        return hitBtcClientImpl.subscribeTicker(subscribeTickerRequest)
            .map { cryptoResponse ->
                mapToCryptoPairModel(cryptoResponse.cryptoResponseData)
            }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun observeCandle(subscribeCandleRequest: SubscribeCandleRequest): Flowable<List<BarData>> {
        return hitBtcClientImpl.subscribeCandle(subscribeCandleRequest)
            .map { candleResponse ->
                Log.d("TAG", "observeCandle: $candleResponse")
                mapToBarData(candleResponse.snapshot, candleResponse.update)
            }
    }
}