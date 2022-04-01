package com.websocket.project.ui.wallet.sell.fiat_drawer

import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.websocket.project.R
import com.websocket.project.databinding.ItemSellFiatBinding
import com.websocket.project.ui.main.fiat.Fiat
import com.websocket.project.ui.wallet.sell.SellFragmentActionListener

class SellFiatDrawerAdapter(
    private val sellFragmentActionListener: SellFragmentActionListener,
    private val sellFiatDrawerFragmentActionListener: SellFiatDrawerFragmentActionListener,
    val localizedResources: Resources?
) : RecyclerView.Adapter<SellFiatDrawerAdapter.SellFiatViewHolder>() {

    private val sellFiatItems = mutableListOf<Fiat>()
    private val searchSellFiatItems = mutableListOf<Fiat>()
    private val searchCurrentFiatItems = mutableListOf<Fiat>()
    private var searchText = ""
    private var currentCheckedFiat: Fiat? = null

    override fun getItemCount() = searchCurrentFiatItems.size

    fun setNewSearchString(text: String) {
        searchText = text.lowercase()
        searchCurrentFiatItems.clear()
        searchCurrentFiatItems.addAll(searchFromList())
        notifyDataSetChanged()
    }

    private fun searchFromList(): List<Fiat> {
        if (searchText.isNotEmpty()) {
            searchSellFiatItems.clear()
            if (localizedResources != null) {
                searchSellFiatItems.addAll(sellFiatItems.filter {
                    localizedResources.getString(it.titleCode).lowercase().contains(searchText) ||
                            localizedResources.getString(it.titleName).lowercase()
                                .contains(searchText)
                })
            }

            sellFiatDrawerFragmentActionListener.currentFiatItemsCount(searchSellFiatItems.size)

            return searchSellFiatItems
        } else {
            return sellFiatItems
        }
    }

    private fun setSpannableSearchText(textView: TextView, fullText: String) {
        if (searchText.isNotEmpty()) {
            val startPos: Int =
                fullText.lowercase().indexOf(searchText.lowercase())
            val endPos: Int = startPos + searchText.length
            if (startPos != -1) {
                val spannable = SpannableString(fullText)
                val highlightSpan =
                    BackgroundColorSpan(textView.context.getColor(R.color.white_opacity_15))
                spannable.setSpan(
                    highlightSpan,
                    startPos,
                    endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                textView.text = spannable
            } else {
                textView.text = fullText
            }
        } else {
            textView.text = fullText
        }
    }

    inner class SellFiatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSellFiatBinding.bind(itemView)
        fun bind(fiatItem: Fiat) {
            with(binding) {
                sellExchangeFiatIconImg.setImageResource(fiatItem.icon)
                itemIsCheckedBinding = fiatItem.isChecked
                localizedResources?.getString(fiatItem.titleCode)
                    ?.let { setSpannableSearchText(sellExchangeFiatTitleCodeText, it) }
                localizedResources?.getString(fiatItem.titleName)
                    ?.let { setSpannableSearchText(sellExchangeFiatTitleNameText, it) }

                fiatItemLayout.setOnClickListener {
                    onFiatSelected(fiatItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellFiatViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sell_fiat, parent, false)
        return SellFiatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SellFiatViewHolder, position: Int) {
        holder.bind(searchCurrentFiatItems[position])
    }

    fun setInitialData(initCheckedFiat: Fiat?) {
        sellFiatItems.addAll(
            mutableListOf(
            Fiat(
                icon = R.drawable.ic_fiat_peso,
                titleCode = R.string.fiat_code_ars,
                titleName = R.string.fiat_name_ars,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fial_byn,
                titleCode = R.string.fiat_code_byn,
                titleName = R.string.fiat_name_byn,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fiat_peso,
                titleCode = R.string.fiat_code_clp,
                titleName = R.string.fiat_name_clp,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fiat_peso,
                titleCode = R.string.fiat_code_cop,
                titleName = R.string.fiat_name_cop,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fiat_eur,
                titleCode = R.string.fiat_code_eur,
                titleName = R.string.fiat_name_eur,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fiat_idr,
                titleCode = R.string.fiat_code_idr,
                titleName = R.string.fiat_name_idr,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fiat_inr,
                titleCode = R.string.fiat_code_inr,
                titleName = R.string.fiat_name_inr,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fiat_kzt,
                titleCode = R.string.fiat_code_kzt,
                titleName = R.string.fiat_name_kzt,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fiat_peso,
                titleCode = R.string.fiat_code_mxn,
                titleName = R.string.fiat_name_mxn,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fiat_myr,
                titleCode = R.string.fiat_code_myr,
                titleName = R.string.fiat_name_myr,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fiat_pen,
                titleCode = R.string.fiat_code_pen,
                titleName = R.string.fiat_name_pen,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fiat_peso,
                titleCode = R.string.fiat_code_php,
                titleName = R.string.fiat_name_php,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fiat_rub,
                titleCode = R.string.fiat_code_rub,
                titleName = R.string.fiat_name_rub,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fiat_thb,
                titleCode = R.string.fiat_code_thb,
                titleName = R.string.fiat_name_thb,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fiat_uah,
                titleCode = R.string.fiat_code_uah,
                titleName = R.string.fiat_name_uah,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fiat_usd,
                titleCode = R.string.fiat_code_usd,
                titleName = R.string.fiat_name_usd,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fiat_vef,
                titleCode = R.string.fiat_code_vef,
                titleName = R.string.fiat_name_vef,
                isChecked = false
            ),
                Fiat(
                icon = R.drawable.ic_fiat_vnd,
                titleCode = R.string.fiat_code_vnd,
                titleName = R.string.fiat_name_vnd,
                isChecked = false
            ))
        )
        searchCurrentFiatItems.addAll(sellFiatItems)
        if (initCheckedFiat != null) {
            val checkedFiat =
                sellFiatItems[getPositionFromListByName(sellFiatItems, initCheckedFiat)]
            checkedFiat.isChecked = true
            currentCheckedFiat = checkedFiat
        }
    }

    private fun getPositionFromListByName(list: List<Fiat>, fiat: Fiat): Int {
        return list.indexOf(fiat)
    }

    private fun onFiatSelected(fiatItem: Fiat) {
        if (currentCheckedFiat != null) {
            val pos = getPositionFromListByName(sellFiatItems, currentCheckedFiat!!)
            if (pos != -1) {
                sellFiatItems[pos].isChecked = false
            }
        }
        currentCheckedFiat = fiatItem
        fiatItem.isChecked = true
        sellFragmentActionListener.onFiatItemClick(fiatItem)
        notifyDataSetChanged()
    }
}