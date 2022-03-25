package com.websocket.project.ui.wallet.sell

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.websocket.project.R
import com.websocket.project.databinding.FragmentSellBinding
import com.websocket.project.ui.base.*
import com.websocket.project.ui.main.MainActivity
import com.websocket.project.ui.main.fiat.Fiat
import com.websocket.project.ui.wallet.sell.fiat_drawer.SellFiatDrawerFragment
import com.websocket.project.ui.wallet.sell.partners.SellPartnersAdapter
import com.websocket.project.ui.wallet.sell.partners.SellPartnersItem
import java.util.*

class SellFragment : BaseFragment<FragmentSellBinding>(), SellFragmentActionListener {

    private lateinit var localizedResources: Resources
    private val viewModel: SellViewModel by viewModels()

    private val sellFiatDrawerFragment: SellFiatDrawerFragment by lazy(LazyThreadSafetyMode.NONE) {
        SellFiatDrawerFragment(this, viewModel.getFiatChosen(), localizedResources)
    }

    private val sellPartnersAdapter: SellPartnersAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SellPartnersAdapter(this@SellFragment, localizedResources)
    }

    override fun initViewBinding(): FragmentSellBinding =
        FragmentSellBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localizedResources = getLocalizedResources(requireContext(), Locale("ru"))

        val fiatDrawerFragmentTransaction: FragmentTransaction =
            (activity as MainActivity).supportFragmentManager.beginTransaction()
        fiatDrawerFragmentTransaction.replace(R.id.sellDrawer, sellFiatDrawerFragment)
        fiatDrawerFragmentTransaction.commit()

        with(binding) {
            sellTopbarLayout.title.text = localizedResources.getString(R.string.sell_title)
            with(sellExchangeLayoutInclude) {
                sellExchangePayTitleText.text = localizedResources.getString(R.string.sell_pay_title)
                sellExchangeReceiveTitleText.text = localizedResources.getString(R.string.sell_receive_title)
                sellExchangePayText.text = "5 133.00"
                sellExchangeReceiveText.text = "100.00"
                sellExchangeMinPayText.text = localizedResources.getString(R.string.sell_min_pay, "513.30")
                sellExchangeMinReceiveText.text = localizedResources.getString(R.string.sell_min_receive, "10.00")
                sellExchangeAvailableText.text = localizedResources.getString(R.string.sell_available_title)
                sellExchangeBestPriceText.text = localizedResources.getString(R.string.sell_best_price_title)
            }
            with(sellPartnersTitleLayoutInclude) {
                sellPartnersTitle.text = localizedResources.getString(R.string.sell_partners_title)
                sellPartnersPartnersTitle.text =
                    localizedResources.getString(R.string.sell_partners_partners_title)
                sellPartnersPriceTitle.text =
                    localizedResources.getString(R.string.sell_partners_price_title)
                sellPartnersYouReceiveTitle.text =
                    localizedResources.getString(R.string.sell_partners_you_receive_title)
            }
            with(sellExchangeLayoutInclude) {
                sellFragmentActionListenerBinding = this@SellFragment
            }

            sellPartnersAdapter.setPartners(mutableListOf(
                SellPartnersItem(
                    icon = R.drawable.ic_partners_simplex,
                    type = R.string.partners_item_type_bank_card,
                    namePartners = R.string.partners_item_name_simplex
                ),
                SellPartnersItem(
                    icon = R.drawable.ic_partners_xanpool,
                    type = R.string.partners_item_type_bank_card,
                    namePartners = R.string.partners_item_name_xanpool
                ),
                SellPartnersItem(
                    icon = R.drawable.ic_partners_moonpay,
                    type = R.string.partners_item_type_apple_pay,
                    namePartners = R.string.partners_item_name_moonpay
                ),
                SellPartnersItem(
                    icon = R.drawable.ic_partners_banxa,
                    type = R.string.partners_item_type_bank_card,
                    namePartners = R.string.partners_item_name_banxa
                ),
                SellPartnersItem(
                    icon = R.drawable.ic_partners_mercuryo,
                    type = R.string.partners_item_type_bank_card,
                    namePartners = R.string.partners_item_name_mercuryo
                ),
                SellPartnersItem(
                    icon = R.drawable.ic_partners_simplex,
                    type = R.string.partners_item_type_bank_card,
                    namePartners = R.string.partners_item_name_simplex
                ),
                SellPartnersItem(
                    icon = R.drawable.ic_partners_xanpool,
                    type = R.string.partners_item_type_bank_card,
                    namePartners = R.string.partners_item_name_xanpool
                ),
                SellPartnersItem(
                    icon = R.drawable.ic_partners_moonpay,
                    type = R.string.partners_item_type_apple_pay,
                    namePartners = R.string.partners_item_name_moonpay
                ),
                SellPartnersItem(
                    icon = R.drawable.ic_partners_banxa,
                    type = R.string.partners_item_type_bank_card,
                    namePartners = R.string.partners_item_name_banxa
                ),
                SellPartnersItem(
                    icon = R.drawable.ic_partners_mercuryo,
                    type = R.string.partners_item_type_bank_card,
                    namePartners = R.string.partners_item_name_mercuryo
                )
            ))
            with(sellPartnersRecycler) {
                addItemDecoration(
                    BaseRecyclerItemDecoration(
                        convertDpToPixel(
                            12f,
                            requireContext()
                        )
                    )
                )
                adapter = sellPartnersAdapter
            }

            sellDrawerLayout.addDrawerListener(object : BaseDrawerListener() {
                override fun onDrawerClosed(drawerView: View) {
                    val imm =
                        (activity as MainActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                }
            })
        }

        observeLiveData()
    }

    override fun onFiatItemClick(fiat: Fiat) {
        binding.sellDrawerLayout.closeDrawer(GravityCompat.END)
        viewModel.setFiatChosen(fiat)
    }

    override fun onFiatBtnClick() {
        binding.sellDrawerLayout.openDrawer(GravityCompat.END)
    }

    override fun onPartnersItemClick(partnersItem: SellPartnersItem) {

    }

    private fun observeLiveData() {
        with(viewModel) {
            observe(fiatChosen) { fiat ->
                with(binding.sellExchangeLayoutInclude) {
                    sellExchangeFiatIconImg.setImageResource(fiat.icon)
                    sellExchangeFiatTitleCodeText.text = localizedResources.getString(fiat.titleCode)
                    sellExchangeFiatTitleNameText.text =
                        localizedResources.getString(fiat.titleName)
                    sellExchangeBestPriceText.text = localizedResources.getString(
                        R.string.sell_best_price_title,
                        51.33.toString().format(2),
                        requireContext().getString(fiat.titleCode)
                    )
                }
            }

            observe(crypto) { crypto ->
                with(binding.sellExchangeLayoutInclude) {
                    sellExchangeCryptoIconImg.setImageResource(crypto.icon)
                    sellExchangeCryptoTitleCodeText.text = localizedResources.getString(crypto.title)
                }
            }
        }
    }
}