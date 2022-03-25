package com.websocket.project.ui.wallet.sell.fiat_drawer

import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
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

    private val sellFiatItems = Fiat.values()
    private val searchSellFiatItems = mutableListOf<Fiat>()
    private val searchCurrentFiatItems: MutableList<Fiat> =
        Fiat.values().toList() as MutableList<Fiat>
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
            return sellFiatItems.asList()
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

    fun setInitCheckedFiat(initCheckedFiat: Fiat?) {
        if (initCheckedFiat != null) {
            val checkedFiat =
                sellFiatItems[getPositionFromListByName(sellFiatItems.asList(), initCheckedFiat)]
            checkedFiat.isChecked = true
            currentCheckedFiat = checkedFiat
        }
    }

    private fun getPositionFromListByName(list: List<Fiat>, fiat: Fiat): Int {
        return list.indexOf(fiat)
    }

    private fun onFiatSelected(fiatItem: Fiat) {
        if (currentCheckedFiat != null) {
            val pos = getPositionFromListByName(sellFiatItems.asList(), currentCheckedFiat!!)
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