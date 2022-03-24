package com.websocket.project.ui.wallet.buy.fiat_drawer

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
import com.websocket.project.databinding.ItemBuyFiatBinding
import com.websocket.project.ui.main.fiat.Fiat
import com.websocket.project.ui.wallet.buy.BuyFragmentActionListener

class BuyFiatDrawerAdapter(
    val buyFragmentActionListener: BuyFragmentActionListener,
    val localizedResources: Resources?
) : RecyclerView.Adapter<BuyFiatDrawerAdapter.BuyFiatViewHolder>() {

    private val buyFiatItems = Fiat.values()
    private val searchBuyFiatItems = mutableListOf<Fiat>()
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
            searchBuyFiatItems.clear()
            if (localizedResources != null) {
                searchBuyFiatItems.addAll(buyFiatItems.filter {
                    localizedResources.getString(it.titleCode).lowercase().contains(searchText) ||
                            localizedResources.getString(it.titleName).lowercase()
                                .contains(searchText)
                })
            }

            return searchBuyFiatItems
        } else {
            return buyFiatItems.asList()
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

    inner class BuyFiatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemBuyFiatBinding.bind(itemView)
        fun bind(fiatItem: Fiat) {
            with(binding) {
                buyExchangeFiatIconImg.setImageResource(fiatItem.icon)
                buyFragmentActionListenerBinding = buyFragmentActionListener
                itemIsCheckedBinding = fiatItem.isChecked
                localizedResources?.getString(fiatItem.titleCode)
                    ?.let { setSpannableSearchText(buyExchangeFiatTitleCodeText, it) }
                localizedResources?.getString(fiatItem.titleName)
                    ?.let { setSpannableSearchText(buyExchangeFiatTitleNameText, it) }

                fiatItemLayout.setOnClickListener {
                    onFiatSelected(fiatItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyFiatViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_buy_fiat, parent, false)
        return BuyFiatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BuyFiatViewHolder, position: Int) {
        holder.bind(searchCurrentFiatItems[position])
    }

    fun setInitCheckedFiat(initCheckedFiat: Fiat?) {
        if (initCheckedFiat != null) {
            val checkedFiat =
                buyFiatItems[getPositionFromListByName(buyFiatItems.asList(), initCheckedFiat)]
            checkedFiat.isChecked = true
            currentCheckedFiat = checkedFiat
        }
    }

    private fun getPositionFromListByName(list: List<Fiat>, fiat: Fiat): Int {
        return list.indexOf(fiat)
    }

    private fun onFiatSelected(fiatItem: Fiat) {
        if (currentCheckedFiat != null) {
            val pos = getPositionFromListByName(buyFiatItems.asList(), currentCheckedFiat!!)
            if (pos != -1) {
                buyFiatItems[pos].isChecked = false
            }
        }
        currentCheckedFiat = fiatItem
        fiatItem.isChecked = true
        buyFragmentActionListener.onFiatItemClick(fiatItem)
        notifyDataSetChanged()
    }
}