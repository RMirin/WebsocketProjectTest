package com.websocket.project.ui.main.crypto

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.websocket.project.databinding.FragmentCryptoPairBinding
import com.websocket.project.ui.base.BaseFragment
import com.websocket.project.ui.main.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoPairFragment: BaseFragment<FragmentCryptoPairBinding>() {

    private val viewModel: MainActivityViewModel by viewModels()

    private val cryptoPairAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MainFragmentCryptoAdapter()
    }

    override fun initViewBinding(): FragmentCryptoPairBinding =
        FragmentCryptoPairBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dividerItemDecoration = DividerItemDecoration(
            activity,
            LinearLayoutManager.VERTICAL
        )
        binding.mainRecycler.addItemDecoration(dividerItemDecoration)
        binding.mainRecycler.adapter = cryptoPairAdapter
    }

    override fun onResume() {
        super.onResume()
        Log.e("TAG", "onResume: subscribe")
        viewModel.ticker.observe(viewLifecycleOwner, { ticker ->
            binding.cryptoPairProgress.visibility = View.GONE
            cryptoPairAdapter.setNewCryptoHashMap(ticker)
//            Log.e("TAG", "onCreate: $ticker")
        })
    }

    override fun onPause() {
        super.onPause()
        Log.e("TAG", "onPause: removeObservers")
        viewModel.ticker.removeObservers(viewLifecycleOwner)
    }

    companion object {
        fun newInstance() = CryptoPairFragment()
    }
}