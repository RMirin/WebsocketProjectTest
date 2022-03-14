package com.websocket.project.ui.withdraw

import com.websocket.project.databinding.FragmentWithdrawBinding
import com.websocket.project.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawFragment :
    BaseFragment<FragmentWithdrawBinding>() {
    override fun initViewBinding(): FragmentWithdrawBinding =
        FragmentWithdrawBinding.inflate(layoutInflater)
}