package com.websocket.project.repository

import com.websocket.project.model.CryptoPairModel
import com.websocket.project.request.SubscribeTickerRequest
import io.reactivex.Flowable

interface WebSocketRepository {
    fun observeTicker(subscribeTickerRequest: SubscribeTickerRequest): Flowable<HashMap<String, CryptoPairModel>>
}