package com.example.shoppinglist.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldFinishActivity = MutableLiveData<Unit>()
    val shouldFinishActivity: LiveData<Unit>
        get() = _shouldFinishActivity

    fun getShopItem(id: Int) {
        _shopItem.value = getShopItemUseCase.getShopItem(id)
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val isInputDataValid = validateInputData(name, count)

        if (isInputDataValid) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
            finishActivity()
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val isInputDataValid = validateInputData(name, count)

        if (isInputDataValid) {
            _shopItem.value?.let {
                val newShopItem = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(newShopItem)
                finishActivity()
            }
        }
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInputData(name: String, count: Int): Boolean {
        var isInputDataValid = true
        if (name.isBlank()) {
            _errorInputName.value = true
            isInputDataValid = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            isInputDataValid = false
        }
        return isInputDataValid
    }

    private fun finishActivity() {
        _shouldFinishActivity.value = Unit
    }
}