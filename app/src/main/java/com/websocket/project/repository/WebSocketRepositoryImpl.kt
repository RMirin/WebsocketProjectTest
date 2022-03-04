package com.websocket.project.repository

import com.websocket.project.converter.mapToCryptoPairModel
import com.websocket.project.data.remote.HitBtcClientImpl
import com.websocket.project.model.CryptoPairModel
import com.websocket.project.request.SubscribeTickerRequest
import io.reactivex.Flowable
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
}