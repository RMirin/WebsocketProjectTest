package com.websocket.project.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.websocket.project.model.CryptoPairModel
import com.websocket.project.response.CryptoResponse
import com.websocket.project.usecases.WebSocketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    webSocketUseCase: WebSocketUseCase
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _permissionState = MutableStateFlow(value = true)
    val permissionState: StateFlow<Boolean> = _permissionState.asStateFlow()

    private val _ticker = MutableLiveData<HashMap<String, CryptoPairModel>>()
    val ticker: LiveData<HashMap<String, CryptoPairModel>>
        get() = _ticker

    init {
        compositeDisposable.add(webSocketUseCase.invoke()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ ticker ->
                _ticker.postValue(ticker)
            }, { e ->
                e.printStackTrace()
            })
        )
    }
}