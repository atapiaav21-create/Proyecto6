package com.example.proyecto6.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Usuario::class, Transaccion::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    abstract fun transaccionDao(): TransaccionDao
}
