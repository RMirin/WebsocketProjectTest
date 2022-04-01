package com.websocket.project.ui.wallet.buy

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.websocket.project.R
import com.websocket.project.databinding.FragmentBuyBinding
import com.websocket.project.ui.base.*
import com.websocket.project.ui.main.MainActivity
import com.websocket.project.ui.main.fiat.Fiat
import com.websocket.project.ui.wallet.buy.fiat_drawer.BuyFiatDrawerFragment
import com.websocket.project.ui.wallet.buy.partners.BuyPartnersAdapter
import com.websocket.project.ui.wallet.buy.partners.BuyPartnersItem
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class BuyFragment : BaseFragment<FragmentBuyBinding>(), BuyFragmentActionListener {

    private lateinit var localizedResources: Resources
    private val viewModel: BuyViewModel by viewModels()

    private val buyFiatDrawerFragment: BuyFiatDrawerFragment by lazy(LazyThreadSafetyMode.NONE) {
        BuyFiatDrawerFragment(this, viewModel.getFiatChosen(), localizedResources)
    }

    private val buyPartnersAdapter: BuyPartnersAdapter by lazy(LazyThreadSafetyMode.NONE) {
        BuyPartnersAdapter(this@BuyFragment, localizedResources)
    }

    override fun initViewBinding(): FragmentBuyBinding =
        FragmentBuyBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localizedResources = getLocalizedResources(requireContext(), Locale("ru"))

        val fiatDrawerFragmentTransaction: FragmentTransaction =
            (activity as MainActivity).supportFragmentManager.beginTransaction()
        fiatDrawerFragmentTransaction.replace(R.id.buyDrawer, buyFiatDrawerFragment)
        fiatDrawerFragmentTransaction.commit()

        with(binding) {
            buyTopbarLayout.title.text = localizedResources.getString(R.string.buy_title)
            with(buyExchangeLayoutInclude) {
                buyExchangePayTitleText.text = localizedResources.getString(R.string.buy_pay_title)
                buyExchangeReceiveTitleText.text = localizedResources.getString(R.string.buy_receive_title)
                buyExchangePayText.text = "5 133.00"
                buyExchangeReceiveText.text = "100.00"
                buyExchangeMinPayText.text = localizedResources.getString(R.string.buy_min_pay, "513.30")
                buyExchangeMinReceiveText.text = localizedResources.getString(R.string.buy_min_receive, "10.00")
                buyExchangeBestPriceText.text = localizedResources.getString(R.string.buy_best_price_title)
            }
            with(buyPartnersTitleLayoutInclude) {
                buyPartnersTitle.text = localizedResources.getString(R.string.buy_partners_title)
                buyPartnersPartnersTitle.text =
                    localizedResources.getString(R.string.buy_partners_partners_title)
                buyPartnersPriceTitle.text =
                    localizedResources.getString(R.string.buy_partners_price_title)
                buyPartnersYouReceiveTitle.text =
                    localizedResources.getString(R.string.buy_partners_you_receive_title)
            }
            with(buyExchangeLayoutInclude) {
                buyFragmentActionListenerBinding = this@BuyFragment
            }

            buyPartnersAdapter.setPartners(mutableListOf(
                BuyPartnersItem(
                    icon = R.drawable.ic_partners_simplex,
                    type = R.string.partners_item_type_bank_card,
                    namePartners = R.string.partners_item_name_simplex
                ),
                BuyPartnersItem(
                    icon = R.drawable.ic_partners_xanpool,
                    type = R.string.partners_item_type_bank_card,
                    namePartners = R.string.partners_item_name_xanpool
                ),
                BuyPartnersItem(
                    icon = R.drawable.ic_partners_moonpay,
                    type = R.string.partners_item_type_apple_pay,
                    namePartners = R.string.partners_item_name_moonpay
                ),
                BuyPartnersItem(
                    icon = R.drawable.ic_partners_banxa,
                    type = R.string.partners_item_type_bank_card,
                    namePartners = R.string.partners_item_name_banxa
                ),
                BuyPartnersItem(
                    icon = R.drawable.ic_partners_mercuryo,
                    type = R.string.partners_item_type_bank_card,
                    namePartners = R.string.partners_item_name_mercuryo
                ),
                BuyPartnersItem(
                    icon = R.drawable.ic_partners_simplex,
                    type = R.string.partners_item_type_bank_card,
                    namePartners = R.string.partners_item_name_simplex
                ),
                BuyPartnersItem(
                    icon = R.drawable.ic_partners_xanpool,
                    type = R.string.partners_item_type_bank_card,
                    namePartners = R.string.partners_item_name_xanpool
                ),
                BuyPartnersItem(
                    icon = R.drawable.ic_partners_moonpay,
                    type = R.string.partners_item_type_apple_pay,
                    namePartners = R.string.partners_item_name_moonpay
                ),
                BuyPartnersItem(
                    icon = R.drawable.ic_partners_banxa,
                    type = R.string.partners_item_type_bank_card,
                    namePartners = R.string.partners_item_name_banxa
                ),
                BuyPartnersItem(
                    icon = R.drawable.ic_partners_mercuryo,
                    type = R.string.partners_item_type_bank_card,
                    namePartners = R.string.partners_item_name_mercuryo
                )
            ))
            with(buyPartnersRecycler) {
                addItemDecoration(
                    BaseRecyclerItemDecoration(
                        convertDpToPixel(
                            12f,
                            requireContext()
                        )
                    )
                )
                adapter = buyPartnersAdapter
            }

            buyDrawerLayout.addDrawerListener(object : BaseDrawerListener() {
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
        binding.buyDrawerLayout.closeDrawer(GravityCompat.START)
        viewModel.setFiatChosen(fiat)
    }

    override fun onFiatBtnClick() {
        binding.buyDrawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onPartnersItemClick(partnersItem: BuyPartnersItem) {
    }

    private fun observeLiveData() {
        with(viewModel) {
            observe(fiatChosen) { fiat ->
                with(binding.buyExchangeLayoutInclude) {
                    buyExchangeFiatIconImg.setImageResource(fiat.icon)
                    buyExchangeFiatTitleCodeText.text = localizedResources.getString(fiat.titleCode)
                    buyExchangeFiatTitleNameText.text =
                        localizedResources.getString(fiat.titleName)
                    buyExchangeBestPriceText.text = localizedResources.getString(
                        R.string.buy_best_price_title,
                        51.33.toString().format(2),
                        requireContext().getString(fiat.titleCode)
                    )
                }
            }

            observe(crypto) { crypto ->
                with(binding.buyExchangeLayoutInclude) {
                    buyExchangeCryptoIconImg.setImageResource(crypto.icon)
                    buyExchangeCryptoTitleCodeText.text = localizedResources.getString(crypto.title)
                }
            }
        }
    }
}