package com.example.inventory.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat


//class Item digunakan untuk menampilkan entity database di aplikasi
@Entity(tableName = "item")
data class Item (

//    mendeklarasikan id dari jenis Int, itemName dari jenis String, itemPrice dari
//    jenis Double, dan quantityInStock dari jenis Int sebagai
//    parameter untuk konstruktor utama.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val itemName: String,
    @ColumnInfo(name = "price")
    val itemPrice: Double,
    @ColumnInfo(name = "quantity")
    val quantityInStock: Int,
    @ColumnInfo(name = "year")
    val year: Int
)

//funtion untuk mengembalikan harga item yang diteruskan dalam
// format mata uang.
fun Item.getFormattedPrice(): String =
    NumberFormat.getCurrencyInstance().format(itemPrice)