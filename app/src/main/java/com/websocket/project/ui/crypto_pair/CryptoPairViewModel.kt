package com.websocket.project.ui.crypto_pair

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.websocket.project.model.CryptoPairModel
import com.websocket.project.usecases.MarketDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CryptoPairViewModel @Inject constructor(
    private val marketDataUseCase: MarketDataUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _ticker = MutableLiveData<HashMap<String, CryptoPairModel>>()
    val ticker: LiveData<HashMap<String, CryptoPairModel>>
        get() = _ticker

    fun subscribeTickers() {
        compositeDisposable.add(
            marketDataUseCase.subscribeTickers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ ticker ->
                    _ticker.postValue(ticker)
                }, { e ->
                    e.printStackTrace()
                })
        )
    }

    fun unsubscribeTickers() {
        /*LifecycleCoroutineScope. {
            webSocketUseCase.unsubscribeTicker()
        }*/
        //webSocketUseCase.unsubscribeTicker()
        compositeDisposable.clear()
    }
}