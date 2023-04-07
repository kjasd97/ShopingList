package com.ulyanenko.shopinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ulyanenko.shopinglist.data.ShopListRepositoryImpl
import com.ulyanenko.shopinglist.domain.*

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)

    private val _inputNameError = MutableLiveData<Boolean>()
    val inputNameError: LiveData<Boolean>
        get() = _inputNameError

    private val _inputCountError = MutableLiveData<Boolean>()
    val inputCountError: LiveData<Boolean>
        get() = _inputCountError

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _canClose = MutableLiveData<Unit>()
    val canClose: LiveData<Unit>
        get() = _canClose


    fun getShopItem(id: Int) {
        val shopItem = getShopItemUseCase.getShopItem(id)
        _shopItem.value = shopItem
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
            _canClose.value=Unit
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsIsValid = validateInputType(name, count)

        if (fieldsIsValid) {
            _shopItem.value?.let {
                val item = it.copy(name=name, count=count)
                editShopItemUseCase.editShopItem(item)
                _canClose.value =Unit
            }
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
            _inputNameError.value = true
            result = false
        }
        if (count <= 0) {
            _inputCountError.value = true
            result = false
        }
        return result
    }

    fun resetInputNameError() {
        _inputNameError.value = false
    }

    fun resetInputCountError() {
        _inputCountError.value = false
    }

}