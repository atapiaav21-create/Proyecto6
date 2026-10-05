package com.example.proyecto6.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertar(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun obtenerPorEmail(email: String): Usuario?

    @Query("SELECT * FROM usuarios LIMIT 1")
    suspend fun obtenerUsuario(): Usuario?
}
