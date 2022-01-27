package com.websocket.project.converter

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.tradingview.lightweightcharts.api.series.models.BarData
import com.tradingview.lightweightcharts.api.series.models.Time
import com.websocket.project.dto.CandleDto
import com.websocket.project.dto.CryptoPairDto
import com.websocket.project.model.CryptoPairModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
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
fun mapCandleToBarData(
    update: HashMap<String, List<CandleDto>>?
): List<BarData> {
    val candleList = mutableListOf<BarData>()
    update?.forEach { (_, s1) ->
        candleList.addAll(convertCandle(s1))
    }
    candleList.sortedBy { it.time.date }

    candleList.forEach {
        Log.d("TAG", "mapCandleToBarData: ${it.time.date}")
    }

    return candleList
}

private fun convertCandle(candle: List<CandleDto>?): List<BarData> {
    val barList = mutableListOf<BarData>()
    candle?.forEach {
        barList.add(
            BarData(
                Time.Utc.fromDate(Date(it.timestamp)),
                it.open,
                it.high,
                it.low,
                it.close
            )
        )
    }
    return barList
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}