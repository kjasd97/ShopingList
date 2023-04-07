package com.ulyanenko.shopinglist.presentation

import androidx.lifecycle.ViewModel
import com.ulyanenko.shopinglist.data.ShopListRepositoryImpl
import com.ulyanenko.shopinglist.domain.*

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)

    fun getShopItem(id: Int): ShopItem {
        return getShopItemUseCase.getShopItem(id)
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsIsValid = validateInputType(name, count)

        if (fieldsIsValid) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsIsValid = validateInputType(name, count)

        if (fieldsIsValid) {
            val shopItem = ShopItem(name, count, true)
            editShopItemUseCase.editShopItem(shopItem)
        }
    }

    fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    fun validateInputType(name: String, count: Int): Boolean {
        var result = true

        if (name.isBlank()) {
            //TODO show error input name
            result = false
        }
        if (count <= 0) {
            //TODO show error input count
            result = false
        }
        return result
    }


}