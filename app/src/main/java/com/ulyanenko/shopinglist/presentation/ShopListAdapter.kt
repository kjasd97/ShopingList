package com.ulyanenko.shopinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ulyanenko.shopinglist.R
import com.ulyanenko.shopinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val layout = when (viewType) {
            DISABLE -> R.layout.item_shop_disabled
            ENABLE -> R.layout.item_shop_enabled
            else -> throw RuntimeException("Unknown view")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopItem = shopList[position]

        holder.view.setOnLongClickListener {
            true
        }

        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()

    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = shopList[position]
        return if (shopItem.enabled) {
            ENABLE
        } else {
            DISABLE
        }
    }


    class ShopListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }

    companion object {
        const val ENABLE = 100
        const val DISABLE = 101
        const val MAX_POOL = 15
    }
}