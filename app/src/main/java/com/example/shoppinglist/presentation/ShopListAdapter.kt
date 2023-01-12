package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import java.lang.RuntimeException

// Лучше наследоваться от ListAdapter, т.к. тогда вычисления будут происходить не в главном потоке
//class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

//    var shopList = listOf<ShopItem>()
//        set(value) {
//            val callback = ShopListDiffCallback(shopList, value)
//            val diffResult = DiffUtil.calculateDiff(callback)
//            diffResult.dispatchUpdatesTo(this)
//            field = value
//        }

    override fun getItemViewType(position: Int): Int {
//        val shopItem = shopList[position]
        val shopItem = getItem(position)
        return if (shopItem.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            else -> throw RuntimeException("Unknown View Type")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        with(holder) {
//            with(shopList[position]) {
            with(getItem(position)) {
                tvItemName.text = name
                tvItemQuantity.text = count.toString()
                itemView.setOnLongClickListener {
                    onShopItemLongClickListener?.invoke(this)
                    true
                }
                itemView.setOnClickListener {
                    onShopItemClickListener?.invoke(this)
                }
            }
        }
    }

//    override fun getItemCount(): Int {
//        return shopList.size
//    }

    companion object {
        const val VIEW_TYPE_DISABLED = 0
        const val VIEW_TYPE_ENABLED = 1
        const val MAX_POOL_SIZE = 15
    }

//    Вынес в отдельный класс
//    class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val tvItemName = itemView.findViewById<TextView>(R.id.tvItemName)
//        val tvItemQuantity = itemView.findViewById<TextView>(R.id.tvItemQuantity)
//
//    }
}