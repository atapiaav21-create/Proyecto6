package com.example.proyecto6.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TransaccionDao {

    @Insert
    suspend fun insertar(transaccion: Transaccion)

    @Query("SELECT * FROM transacciones ORDER BY id DESC")
    suspend fun obtenerTodas(): List<Transaccion>
}
