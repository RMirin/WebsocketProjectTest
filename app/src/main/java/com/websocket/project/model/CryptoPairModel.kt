package com.websocket.project.model

data class CryptoPairModel(
    var icon: Int?,
    var name: String?,
    var timestamp: Long?,
    var sequence: String?,
    var asks: String?,
    var bids: String?,
    var priceChangePercent: Double
) {
    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = (prime * result
                + if (asks == null) 0 else asks.hashCode())
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null) return false
        if (javaClass != obj.javaClass) return false
        val other: CryptoPairModel = obj as CryptoPairModel
        if (asks == null) {
            if (other.asks != null) return false
        } else if (!asks.equals(other.asks)) return false
        return true
    }
}