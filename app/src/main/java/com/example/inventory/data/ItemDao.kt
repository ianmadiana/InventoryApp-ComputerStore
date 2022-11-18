package com.example.inventory.data

import androidx.room.*
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


// TODO 1: implementasi dari Data Access Object (DAO)
@Dao
interface ItemDao {
    //argumen onConflict berfungsi mengabaikan item baru jika kunci utama sudah ada di database
    //memasukkan item ke database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)
    //memperbarui item
    @Update
    suspend fun update(item: Item)
    //meghapus item
    @Delete
    suspend fun delete(item: Item)

    //kueri untuk menampilkan item
    @Query("SELECT * from item WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Query("SELECT * from item ORDER BY name ASC")
    fun getItems(): Flow<List<Item>>
}