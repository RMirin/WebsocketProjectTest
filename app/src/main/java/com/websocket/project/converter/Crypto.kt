package com.websocket.project.converter

import com.websocket.project.dto.CryptoPairDto
import com.websocket.project.model.CryptoPairModel
import kotlin.math.round

fun mapToCryptoPairModel(map: Map<String, CryptoPairDto>?) : HashMap<String, CryptoPairModel> {
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

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}