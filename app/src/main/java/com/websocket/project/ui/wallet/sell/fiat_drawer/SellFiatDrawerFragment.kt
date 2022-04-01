package com.websocket.project.ui.wallet.sell.fiat_drawer

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import com.jakewharton.rxbinding4.view.focusChanges
import com.jakewharton.rxbinding4.widget.textChanges
import com.websocket.project.R
import com.websocket.project.databinding.DrawerFragmentSellFiatBinding
import com.websocket.project.ui.base.*
import com.websocket.project.ui.main.fiat.Fiat
import com.websocket.project.ui.wallet.sell.SellFragmentActionListener
import java.util.*

class SellFiatDrawerFragment(
    private val sellFragmentActionListener: SellFragmentActionListener,
    checkedFiat: Fiat?,
    private val localizedResources: Resources?
) : BaseFragment<DrawerFragmentSellFiatBinding>(), SellFiatDrawerFragmentActionListener {

    private var currentCheckedFiat = checkedFiat

    private val sellFiatDrawerAdapter: SellFiatDrawerAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SellFiatDrawerAdapter(sellFragmentActionListener, this, localizedResources)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val localizedResources = getLocalizedResources(requireContext(), Locale("ru"))
        with(binding) {
            showContent = true
            sellFiatDrawerFragmentActionListenerBinding = this@SellFiatDrawerFragment

            sellFiatSearchEditText.hint = localizedResources.getString(R.string.buy_fiat_search_hint)
            sellFiatSearchEditText.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                12f
            )
            sellFiatNothingFoundText.text = localizedResources.getString(R.string.common_empty)

            with(sellFiatRecycler) {
                addItemDecoration(
                    BaseRecyclerItemDecoration(
                        convertDpToPixel(
                            16f,
                            requireContext()
                        )
                    )
                )
                itemAnimator = null
                sellFiatDrawerAdapter.setInitialData(currentCheckedFiat)
                adapter = sellFiatDrawerAdapter
            }
        }

        observeSearchChanges()
    }

    override fun initViewBinding(): DrawerFragmentSellFiatBinding =
        DrawerFragmentSellFiatBinding.inflate(layoutInflater)

    private fun observeSearchChanges() {
        with(binding) {
            sellFiatSearchEditText.focusChanges().subscribe { focus ->
                if (focus) {
                    sellFiatSearchImg.visibility = View.GONE
                    sellFiatSearchEditText.textChanges()
                        .subscribe { text ->
                            searchFromList(text.toString())
                            if (text.isEmpty()) {
                                sellFiatSearchEditText.setTextSize(
                                    TypedValue.COMPLEX_UNIT_SP,
                                    12f
                                )
                                sellFiatClearBtn.visibility = View.GONE
                            } else {
                                sellFiatSearchEditText.setTextSize(
                                    TypedValue.COMPLEX_UNIT_SP,
                                    14f
                                )
                                sellFiatClearBtn.visibility = View.VISIBLE
                            }
                        }
                } else {
                    sellFiatSearchImg.visibility = View.VISIBLE
                    sellFiatClearBtn.visibility = View.GONE
                }
            }
            sellFiatClearBtn.accessibleTouchTarget()
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
////        sellFiatDrawerAdapter.uncheckOnDestroyFragment()
//    }

    private fun searchFromList(text: String) {
        sellFiatDrawerAdapter.setNewSearchString(text)
    }

    override fun currentFiatItemsCount(currentFiatItemsCount: Int) {
        binding.showContent = currentFiatItemsCount != 0
    }

    override fun onClearSearchBtnClick() {
        binding.sellFiatSearchEditText.text.clear()
    }
}