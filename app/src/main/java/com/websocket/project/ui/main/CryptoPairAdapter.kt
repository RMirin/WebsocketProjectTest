package com.websocket.project.ui.main

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.websocket.project.R
import com.websocket.project.databinding.ItemCryptoPairBinding
import com.websocket.project.model.CryptoPairModel
import com.websocket.project.ui.base.BaseRecyclerViewAdapter
import com.websocket.project.ui.base.BaseViewHolder
import com.websocket.project.ui.base.inflateWithBinding
import com.websocket.project.ui.base.spanAll
import java.math.RoundingMode
import java.text.DecimalFormat

class CryptoPairAdapter : BaseRecyclerViewAdapter() {

    inner class ActorDiffCallback(
        private val oldList: List<CryptoPairModel>,
        private val newList: List<CryptoPairModel>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].name == newList[newItemPosition].name
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList[oldItemPosition].asks == newList[newItemPosition].asks) && (oldList[oldItemPosition].bids == newList[newItemPosition].bids)
        }
    }

    private val cryptoPairList: MutableList<CryptoPairModel> = mutableListOf()

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
            val roundedPercent = roundOffDecimal(item.priceChangePercent)

            itemCryptoPairBinding.also {
                with(it) {

                    val context = it.root.context
                    cryptoPairName.text = context.getString(
                        R.string.name_format_with_percent,
                        item.name,
                        roundedPercent
                    )
                    cryptoPairAsk.text = item.asks
                    cryptoPairBid.text = item.bids

                    if (item.priceChangePercent != 0.0) {
                        cryptoPairName.spanAll(
                            if (item.priceChangePercent > 0) ForegroundColorSpan(
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
                        cryptoPairName.setTextColor(Color.WHITE)
                    }
                }
            }
        }
    }

    fun roundOffDecimal(number: Double): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number)
    }

    fun swap(mActualData: List<CryptoPairModel>) {
        val diffCallback = ActorDiffCallback(this.cryptoPairList, mActualData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.cryptoPairList.clear()
        this.cryptoPairList.addAll(mActualData)
        diffResult.dispatchUpdatesTo(this)
    }
}