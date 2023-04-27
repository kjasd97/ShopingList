package com.ulyanenko.shopinglist.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopListDAO {

    @Query("SELECT * from shop_item")
    fun getShopList(): LiveData<List<ShopItemDbModel>>

    @Query("SELECT * from shop_item")
    fun getShopListCursor(): Cursor


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItemForProvider(shopItemDbModel: ShopItemDbModel)

    @Query("DELETE FROM shop_item WHERE id=:shopItemId")
    suspend fun deleteShopItem(shopItemId: Int)

    @Query("DELETE FROM shop_item WHERE id=:shopItemId")
    fun deleteShopItemForProvider(shopItemId: Int):Int

    @Query("SELECT * FROM shop_item WHERE id=:shopItemId LIMIT 1")
    suspend fun getShopItem(shopItemId: Int): ShopItemDbModel
}