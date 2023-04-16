package com.ulyanenko.shopinglist.domain

class GetShopItemUseCase (private val shopListRepository: ShopListRepository) {

    suspend fun getShopItem(id: Int): ShopItem {
      return  shopListRepository.getShopItem(id)
    }

}