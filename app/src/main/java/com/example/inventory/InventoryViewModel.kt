package com.example.inventory

import androidx.lifecycle.*
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao: ItemDao) : ViewModel() {

//  Cache semua item dari database menggunakan LiveData
    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    fun isStockAvailable(item: Item): Boolean {
        return (item.quantityInStock > 0)
    }

    fun updateItem(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String,
        itemRilis: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemPrice, itemCount, itemRilis)
        updateItem(updatedItem)
    }

    private fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }

    fun sellItem(item: Item) {
        if (item.quantityInStock > 0) {
            // Decrease the quantity by 1
            val newItem = item.copy(quantityInStock = item.quantityInStock - 1)
            updateItem(newItem)
        }
    }

    //funtion public yang menggunakan 3 string untuk detail Item.
    fun addNewItem(itemName: String, itemPrice: String, itemCount: String, itemRilis: String) {
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount, itemRilis)
        insertItem(newItem)
    }

    //funtion private yang berfunbgsi untuk mengambil objek Item
// dan menambahkan data ke database dengan cara yang tidak memblokir
    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
    }

//funtion private yang berfunbgsi untuk mengambil objek Item
// dan menambahkan data ke database dengan cara yang tidak memblokir



//funtion private yang menggunakan 3 string dan menampilkan instance Item.
    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String, itemRilis: String): Item {
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt(),
            year = itemRilis.toInt()
        )
    }

//funtion public yang menggunakan 3 string untuk detail Item.
    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String, itemRilis: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank() || itemRilis.isBlank()) {
            return false
        }
        return true
    }

    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String,
        itemRilis: String
    ): Item {
        return Item(
            id = itemId,
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt(),
            year = itemRilis.toInt()
        )
    }
}

//membuat class InventoryViewModelFactory untuk membuat  instance InventoryViewModel.
class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}