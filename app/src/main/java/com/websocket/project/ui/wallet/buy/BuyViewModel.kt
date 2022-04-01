package com.websocket.project.ui.wallet.buy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.websocket.project.R
import com.websocket.project.ui.main.crypto.Crypto
import com.websocket.project.ui.main.fiat.Fiat
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BuyViewModel @Inject constructor(
) : ViewModel() {

    private val _fiatChosen = MutableLiveData(
        Fiat(
            icon = R.drawable.ic_fiat_peso,
            titleCode = R.string.fiat_code_php,
            titleName = R.string.fiat_name_php,
            isChecked = false
        )
    )
    val fiatChosen: LiveData<Fiat>
        get() = _fiatChosen

    private val _crypto = MutableLiveData(Crypto.USDT)
    val crypto: LiveData<Crypto>
        get() = _crypto

    fun setFiatChosen(fiat: Fiat) {
        _fiatChosen.postValue(fiat)
    }

    fun getFiatChosen(): Fiat? {
        return _fiatChosen.value
    }

    fun setCrypto(crypto: Crypto) {
        _crypto.postValue(crypto)
    }

    fun getCrypto(): Crypto? {
        return _crypto.value
    }
}