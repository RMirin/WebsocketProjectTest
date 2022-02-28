package com.websocket.project.ui.support.appeal_category

import androidx.annotation.StringRes
import com.websocket.project.R

enum class AppealCategory(
    @StringRes val title: Int
) {
    FIAT_WITHDRAWAL(
        title = R.string.support_appeal_category_fiat_withdrawal
    ),
    DEPOSIT_IN_CRYPTOCURRENCY(
        title = R.string.support_appeal_category_deposit_in_cryptocurrency
    ),
    FIAT_DEPOSIT(
        title = R.string.support_appeal_category_fiat_deposit
    ),
    WITHDRAWAL_IN_CRYPTOCURRENCY(
        title = R.string.support_appeal_category_withdrawal_in_cryptocurrency
    ),
    TRADE_PROBLEM(
        title = R.string.support_appeal_category_trade_problem
    ),
    TRADE_ISSUE(
        title = R.string.support_appeal_category_trade_issue
    ),
    RESET_2FA(
        title = R.string.support_appeal_category_reset_2fa
    ),
    CHANGE_EMAIL(
        title = R.string.support_appeal_category_change_email
    ),
    PROOF_OF_IDENTITY(
        title = R.string.support_appeal_category_proof_of_identity
    ),
    TECHNICAL_ISSUE(
        title = R.string.support_appeal_category_technical_issue
    ),
    CLOSING_AN_ACCOUNT(
        title = R.string.support_appeal_category_closing_an_account
    ),
    OTHER(
        title = R.string.support_appeal_category_other
    )
}