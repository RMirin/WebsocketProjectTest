package com.websocket.project.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.websocket.project.databinding.FragmentCryptoPairBinding
import com.websocket.project.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoPairFragment: BaseFragment<FragmentCryptoPairBinding>() {

    private val viewModel: MainActivityViewModel by viewModels()

    private val cryptoPairAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CryptoPairAdapter()
    }

    override fun initViewBinding(): FragmentCryptoPairBinding =
        FragmentCryptoPairBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainRecycler.adapter = cryptoPairAdapter

        viewModel.ticker.observe(viewLifecycleOwner, { ticker ->
            cryptoPairAdapter.swap(ticker)
            Log.e("TAG", "onCreate: $ticker")
        })
    }

    companion object {
        fun newInstance() = CryptoPairFragment()
    }
}