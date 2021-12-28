package com.websocket.project.ui.main

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.websocket.project.R
import com.websocket.project.databinding.ItemCryptoPairBinding
import com.websocket.project.model.CryptoPairModel
import com.websocket.project.ui.base.*
import java.math.RoundingMode
import java.text.DecimalFormat

class CryptoPairAdapter : BaseRecyclerViewAdapter() {

    inner class ActorDiffCallback(
        private val oldList: List<Pair<String, CryptoPairModel>>,
        private val newList: List<Pair<String, CryptoPairModel>>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].first == newList[newItemPosition].first
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].second.hashCode() == newList[newItemPosition].second.hashCode()
        }
    }

    private val cryptoMap = HashMap<String, CryptoPairModel>()
    private val newList = mutableListOf<Pair<String, CryptoPairModel>>()
    private val cryptoPairList: MutableList<Pair<String, CryptoPairModel>> = mutableListOf()

    override fun getItemCount() = cryptoPairList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return CryptoPairViewHolder(parent.inflateWithBinding(R.layout.item_crypto_pair))
    }

    private inner class CryptoPairViewHolder(
        private val itemCryptoPairBinding: ItemCryptoPairBinding
    ) : BaseViewHolder(itemCryptoPairBinding) {
        override fun bind(position: Int) {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.FLOOR
            val item = cryptoPairList[position]
            val itemPriceChangePercent = item.second.priceChangePercent
            val roundedPercent = roundOffDecimal(itemPriceChangePercent)

            itemCryptoPairBinding.also {
                with(it) {

                    val context = it.root.context
                    cryptoPairName.text = context.getString(
                        R.string.name_format_with_percent,
                        item.first,
                        roundedPercent
                    )
                    cryptoPairAsk.text = item.second.asks
                    cryptoPairBid.text = item.second.bids

                    if (itemPriceChangePercent != 0.0) {
                        cryptoPairName.spanAll(
                            if (itemPriceChangePercent > 0) ForegroundColorSpan(
                                ContextCompat.getColor(
                                    context,
                                    R.color.price_up
                                )
                            ) else ForegroundColorSpan(
                                ContextCompat.getColor(
                                    context,
                                    R.color.price_down
                                )
                            ),
                            roundedPercent
                        )
                    } else {
                        cryptoPairName.setTextColor(Color.BLACK)
                    }
                }
            }
        }
    }

    fun setNewCryptoHashMap(mData: HashMap<String, CryptoPairModel>) {
        cryptoMap.putAll(mData)
        newList.clear()
        cryptoMap.forEach { (name, cryptoPairModel) ->
            newList.add(Pair(name, cryptoPairModel))
        }
        swap(newList)
    }

    private fun swap(newCryptoList: List<Pair<String, CryptoPairModel>>) {
        val diffCallback = ActorDiffCallback(cryptoPairList, newCryptoList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        cryptoPairList.clear()
        cryptoPairList.addAll(newCryptoList)
        diffResult.dispatchUpdatesTo(this)
    }
}