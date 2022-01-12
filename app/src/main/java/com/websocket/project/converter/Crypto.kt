package com.websocket.project.converter

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.tradingview.lightweightcharts.api.series.models.BarData
import com.tradingview.lightweightcharts.api.series.models.Time
import com.websocket.project.dto.CryptoPairDto
import com.websocket.project.model.CryptoPairModel
import com.websocket.project.response.candle_response.Candle
import kotlin.math.round

fun mapToCryptoPairModel(map: Map<String, CryptoPairDto>?): HashMap<String, CryptoPairModel> {
    val cryptoPairModelMap = HashMap<String, CryptoPairModel>()

    if (map != null) {
        for (pair in map) {
            val token = pair.value
            val cryptoPairModel = CryptoPairModel(
                name = pair.key,
                timestamp = token.timestamp,
                sequence = token.sequence,
                asks = token.asks,
                bids = token.bids,
                priceChangePercent = token.priceChangePercent?.round(2) ?: 0.00
            )
            cryptoPairModelMap[pair.key] = cryptoPairModel
        }
    }

    return cryptoPairModelMap
}

@RequiresApi(Build.VERSION_CODES.N)
fun mapToBarData(
    snapshot: HashMap<String, List<Candle>>?,
    update: HashMap<String, List<Candle>>?
): MutableList<BarData> {
    val candleList = mutableListOf<BarData>()
    candleList.addAll(convertCandle(snapshot?.get("USDTBTC")))
    candleList.addAll(convertCandle(update?.get("USDTBTC")))
    /*snapshot?.forEach { (_, s1) ->
        Log.d("MAPPP", "mapToBarData: ${s1[0].timestamp}")
        candleList.addAll(convertCandle(s1))
    }
    update?.forEach { (_, s1) ->
        candleList.addAll(convertCandle(s1))
    }
    candleList.forEach{
        Log.d("MAPPP", "mapToBarData: ${it.time}")
    }*/

    return candleList
}

object list {
    //val list
}

private fun convertCandle(candle: List<Candle>?): List<BarData> {
    val barList = mutableListOf<BarData>()
    candle?.forEach {
        barList.add(BarData(Time.Utc(it.timestamp), it.open, it.high, it.low, it.close))
    }
    return barList
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}