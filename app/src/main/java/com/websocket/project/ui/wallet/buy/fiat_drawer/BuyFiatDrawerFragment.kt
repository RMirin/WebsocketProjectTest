package com.websocket.project.ui.wallet.buy.fiat_drawer

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding4.view.focusChanges
import com.jakewharton.rxbinding4.widget.textChanges
import com.websocket.project.R
import com.websocket.project.databinding.DrawerFragmentBuyFiatBinding
import com.websocket.project.ui.base.*
import com.websocket.project.ui.main.MainActivity
import com.websocket.project.ui.main.fiat.Fiat
import com.websocket.project.ui.wallet.buy.BuyFragmentActionListener
import java.util.*

class BuyFiatDrawerFragment(
    private val buyFragmentActionListener: BuyFragmentActionListener,
    checkedFiat: Fiat?,
    private val localizedResources: Resources?
) :
    BaseFragment<DrawerFragmentBuyFiatBinding>(), BuyFiatDrawerFragmentActionListener {

    private var currentCheckedFiat = checkedFiat

    private val buyFiatDrawerAdapter: BuyFiatDrawerAdapter by lazy(LazyThreadSafetyMode.NONE) {
        BuyFiatDrawerAdapter(buyFragmentActionListener, localizedResources)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val localizedResources = getLocalizedResources(requireContext(), Locale("ru"))

        with(binding) {
            buyFiatDrawerFragmentActionListenerBinding = this@BuyFiatDrawerFragment

            buyFiatSearchEditText.hint = localizedResources?.getString(R.string.buy_fiat_search_hint)

            with(buyFiatRecycler) {
                addItemDecoration(
                    BaseRecyclerItemDecoration(
                        convertDpToPixel(
                            16f,
                            requireContext()
                        )
                    )
                )
                itemAnimator = null
                buyFiatDrawerAdapter.setInitCheckedFiat(currentCheckedFiat)
                adapter = buyFiatDrawerAdapter
            }
        }

        observeSearchChanges()
    }

    override fun initViewBinding(): DrawerFragmentBuyFiatBinding =
        DrawerFragmentBuyFiatBinding.inflate(layoutInflater)

    private fun observeSearchChanges() {
        with(binding) {
            buyFiatSearchEditText.focusChanges().subscribe { focus ->
                if (focus) {
                    buyFiatSearchImg.visibility = View.GONE
                    buyFiatSearchEditText.textChanges()
                        .subscribe { text ->
                            searchFromList(text.toString())
                            if (text.isEmpty()) {
                                buyFiatClearBtn.visibility = View.GONE
                            } else {
                                buyFiatClearBtn.visibility = View.VISIBLE
                            }
                        }
                } else {
                    buyFiatSearchImg.visibility = View.VISIBLE
                    buyFiatClearBtn.visibility = View.GONE
                }
            }
            buyFiatClearBtn.accessibleTouchTarget()
        }
    }

    private fun searchFromList(text: String) {
        buyFiatDrawerAdapter.setNewSearchString(text)
    }

    override fun onClearSearchBtnClick() {
        binding.buyFiatSearchEditText.text.clear()
    }
}