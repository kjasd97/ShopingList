package com.ulyanenko.shopinglist.data

import com.ulyanenko.shopinglist.domain.ShopItem
import javax.inject.Inject

class ShopItemMapper @Inject constructor() {

    fun mapFromEntityToDbModel(shopItem: ShopItem):ShopItemDbModel{
        return ShopItemDbModel(
            id = shopItem.id,
            name = shopItem.name,
            count = shopItem.count,
            enabled = shopItem.enabled
        )
    }

    fun mapFromDbModelToEntity(shopItemDbModel: ShopItemDbModel):ShopItem{
        return ShopItem(
            name = shopItemDbModel.name,
            count = shopItemDbModel.count,
            id = shopItemDbModel.id,
            enabled = shopItemDbModel.enabled
        )
    }

    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>): List<ShopItem>{
        return list.map {
            mapFromDbModelToEntity(it)
        }
    }

    fun mapListEntityToListDbModel(list: List<ShopItem>): List<ShopItemDbModel>{
        return list.map {
            mapFromEntityToDbModel(it)
        }
    }
}