package com.example.proyecto6.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transacciones")
data class Transaccion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fecha: String,
    val monto: Double,
    val descripcion: String,
    val tipo: String
)
