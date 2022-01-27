package com.websocket.project.ui.candle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tradingview.lightweightcharts.api.series.models.BarData
import com.websocket.project.usecases.MarketDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CandleViewModel @Inject constructor(
    private val marketDataUseCase: MarketDataUseCase
) : ViewModel() {

    private var firstMessage = true

    private val compositeDisposable = CompositeDisposable()

    //Get flag from shared prefs
    private val _permissionState = MutableStateFlow(value = true)
    val permissionState: StateFlow<Boolean> = _permissionState.asStateFlow()

    private val _candleUpdate = MutableLiveData<BarData>()
    val candleUpdate: LiveData<BarData>
        get() = _candleUpdate

    private val _candleSnapshot = MutableLiveData<List<BarData>>()
    val candleSnapshot: LiveData<List<BarData>>
        get() = _candleSnapshot


    fun unsubscribeCandle(pairName: String) {
        MainScope().launch(Dispatchers.IO) {
            marketDataUseCase.unsubscribeCandle(pairName)
        }
        //webSocketUseCase.unsubscribeCandle(pairName)
        compositeDisposable.dispose()
    }

    fun subscribeCandle(pairName: String) {
        compositeDisposable.add(
            marketDataUseCase.subscribeCandle(pairName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ candle ->
                    if (firstMessage) {
                        _candleSnapshot.postValue(candle)
                        firstMessage = false
                    } else {
                        _candleUpdate.postValue(candle[0])
                    }
                }, { e ->
                    e.printStackTrace()
                })
        )
    }

}