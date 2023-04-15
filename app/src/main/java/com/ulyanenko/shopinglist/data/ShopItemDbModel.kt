package com.ulyanenko.shopinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ulyanenko.shopinglist.domain.ShopItem

@Entity(tableName = "shop_item")
data class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int ,
    val name: String,
    val count: Int,
    val enabled: Boolean
    ) {
}