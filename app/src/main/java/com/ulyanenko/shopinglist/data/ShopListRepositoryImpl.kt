package com.ulyanenko.shopinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.ulyanenko.shopinglist.domain.ShopItem
import com.ulyanenko.shopinglist.domain.ShopListRepository

class ShopListRepositoryImpl (application: Application): ShopListRepository {

    private val shopListDAO = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopItemMapper()


    override fun addShopItem(shopItem: ShopItem) {
    shopListDAO.addShopItem(mapper.mapFromEntityToDbModel(shopItem))
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopListDAO.deleteShopItem(shopItem.id)
    }

    override fun editShopItem(shopItem: ShopItem) {
        shopListDAO.addShopItem(mapper.mapFromEntityToDbModel(shopItem))

    }

    override fun getShopItem(id: Int): ShopItem {
       val dbModel = shopListDAO.getShopItem(id)
       return mapper.mapFromDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData <List<ShopItem>> {
      return  Transformations.map(shopListDAO.getShopList()){
          mapper.mapListDbModelToListEntity(it)
      }
    }

}