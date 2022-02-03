package com.websocket.project.data.remote.wallet

import javax.inject.Inject

class WalletClientImpl @Inject constructor(
    private val walletApi: WalletApi
) : WalletClient {
}