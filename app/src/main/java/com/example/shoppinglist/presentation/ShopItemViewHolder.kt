package com.example.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class ShopItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val tvItemName = itemView.findViewById<TextView>(R.id.tvItemName)
    val tvItemQuantity = itemView.findViewById<TextView>(R.id.tvItemQuantity)
}