package com.websocket.project.ui.main.crypto

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.websocket.project.R

enum class Crypto(
    @DrawableRes val icon: Int,
    @StringRes val title: Int
) {
    AAVE(
        icon = R.drawable.ic_crypto_aave,
        title = R.string.crypto_aave
    ),
    ADA(
        icon = R.drawable.ic_crypto_ada,
        title = R.string.crypto_ada
    ),
    AVAX(
        icon = R.drawable.ic_crypto_avax,
        title = R.string.crypto_avax
    ),
    BCH(
        icon = R.drawable.ic_crypto_bch,
        title = R.string.crypto_bch
    ),
    BNB(
        icon = R.drawable.ic_crypto_bnb,
        title = R.string.crypto_bnb
    ),
    BTC(
        icon = R.drawable.ic_crypto_btc,
        title = R.string.crypto_btc
    ),
    DOT(
        icon = R.drawable.ic_crypto_dot,
        title = R.string.crypto_dot
    ),
    EOS(
        icon = R.drawable.ic_crypto_eos,
        title = R.string.crypto_eos
    ),
    ETH(
        icon = R.drawable.ic_crypto_eth,
        title = R.string.crypto_eth
    ),
    LTC(
        icon = R.drawable.ic_crypto_ltc,
        title = R.string.crypto_ltc
    ),
    MANA(
        icon = R.drawable.ic_crypto_mana,
        title = R.string.crypto_mana
    ),
    SHIB(
        icon = R.drawable.ic_crypto_shib,
        title = R.string.crypto_shib
    ),
    TRX(
        icon = R.drawable.ic_crypto_trx,
        title = R.string.crypto_trx
    ),
    UNI(
        icon = R.drawable.ic_crypto_uni,
        title = R.string.crypto_uni
    ),
    XLM(
        icon = R.drawable.ic_crypto_xlm,
        title = R.string.crypto_xlm
    ),
    XRP(
        icon = R.drawable.ic_crypto_aave,
        title = R.string.crypto_aave
    ),
    ZEC(
        icon = R.drawable.ic_crypto_zec,
        title = R.string.crypto_zec
    ),
    USDT(
        icon = R.drawable.ic_crypto_usdt,
        title = R.string.crypto_usdt
    );
}