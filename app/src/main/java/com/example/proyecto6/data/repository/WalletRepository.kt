package com.example.proyecto6.data.repository

import com.example.proyecto6.data.local.SecurityUtils
import com.example.proyecto6.data.local.Transaccion
import com.example.proyecto6.data.local.TransaccionDao
import com.example.proyecto6.data.local.Usuario
import com.example.proyecto6.data.local.UsuarioDao
import com.example.proyecto6.data.remote.ApiUser
import com.example.proyecto6.data.remote.RetrofitClient

class WalletRepository(
    private val usuarioDao: UsuarioDao,
    private val transaccionDao: TransaccionDao
) {

    suspend fun registrarUsuario(
        nombre: String,
        email: String,
        password: String
    ) {
        val usuario = Usuario(
            nombre = nombre,
            email = email,
            passwordHash = SecurityUtils.hashPassword(password)
        )

        usuarioDao.insertar(usuario)
    }

    suspend fun iniciarSesion(
        email: String,
        password: String
    ): Usuario? {

        val usuario = usuarioDao.obtenerPorEmail(email)

        if (usuario != null) {
            val passwordHash = SecurityUtils.hashPassword(password)

            if (usuario.passwordHash == passwordHash) {
                return usuario
            }
        }

        return null
    }

    suspend fun obtenerUsuarioActual(): Usuario? {
        return usuarioDao.obtenerUsuario()
    }

    suspend fun guardarTransaccion(transaccion: Transaccion) {
        transaccionDao.insertar(transaccion)
    }

    suspend fun obtenerTransacciones(): List<Transaccion> {
        return transaccionDao.obtenerTodas()
    }

    suspend fun obtenerUsuariosApi(): Result<List<ApiUser>> {
        return try {
            Result.success(RetrofitClient.api.obtenerUsuarios())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
