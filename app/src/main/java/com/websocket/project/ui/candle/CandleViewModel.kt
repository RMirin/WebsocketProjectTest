package com.websocket.project.ui.candle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tradingview.lightweightcharts.api.series.common.SeriesData
import com.tradingview.lightweightcharts.api.series.models.BarData
import com.websocket.project.model.CryptoPairModel
import com.websocket.project.usecases.WebSocketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class CandleViewModel @Inject constructor(
    private val webSocketUseCase: WebSocketUseCase
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    //Get flag from shared prefs
    private val _permissionState = MutableStateFlow(value = true)
    val permissionState: StateFlow<Boolean> = _permissionState.asStateFlow()

    private val _candle = MutableLiveData<List<BarData>>()
    val candle: LiveData<List<BarData>>
        get() = _candle

    fun getCandle(pairName:String){
        compositeDisposable.add(webSocketUseCase.getCandleCryptoPair(pairName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ candle ->
                _candle.postValue(candle)
            }, { e ->
                e.printStackTrace()
            })
        )
    }
}