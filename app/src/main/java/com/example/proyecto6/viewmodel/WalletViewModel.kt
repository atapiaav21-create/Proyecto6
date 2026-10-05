package com.example.proyecto6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto6.data.local.Transaccion
import com.example.proyecto6.data.local.Usuario
import com.example.proyecto6.data.remote.ApiUser
import com.example.proyecto6.data.repository.WalletRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WalletViewModel(
    private val repository: WalletRepository
) : ViewModel() {

    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> = _usuario

    private val _transacciones = MutableStateFlow<List<Transaccion>>(emptyList())
    val transacciones: StateFlow<List<Transaccion>> = _transacciones

    private val _usuariosApi = MutableStateFlow<List<ApiUser>>(emptyList())
    val usuariosApi: StateFlow<List<ApiUser>> = _usuariosApi

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    fun registrarUsuario(
        nombre: String,
        email: String,
        password: String
    ) {
        if (email.isBlank() || password.isBlank()) {
            _mensaje.value = "Completa correo y contraseña"
            return
        }

        viewModelScope.launch {
            try {
                repository.registrarUsuario(
                    nombre = nombre,
                    email = email,
                    password = password
                )

                _mensaje.value = "Usuario registrado correctamente"

            } catch (e: Exception) {
                _mensaje.value = "Error al registrar usuario"
            }
        }
    }

    fun iniciarSesion(
        email: String,
        password: String
    ) {
        if (email.isBlank() || password.isBlank()) {
            _mensaje.value = "Completa correo y contraseña"
            return
        }

        viewModelScope.launch {
            try {
                val usuarioEncontrado = repository.iniciarSesion(
                    email = email,
                    password = password
                )

                if (usuarioEncontrado != null) {
                    _usuario.value = usuarioEncontrado
                    _mensaje.value = "Inicio de sesión correcto"
                } else {
                    _mensaje.value = "Correo o contraseña incorrectos"
                }

            } catch (e: Exception) {
                _mensaje.value = "Error de acceso"
            }
        }
    }

    fun cargarTransacciones() {
        viewModelScope.launch {
            try {
                _transacciones.value = repository.obtenerTransacciones()
            } catch (e: Exception) {
                _mensaje.value = "Error al cargar transacciones"
            }
        }
    }

    fun guardarTransaccion(transaccion: Transaccion) {
        viewModelScope.launch {
            try {
                repository.guardarTransaccion(transaccion)
                cargarTransacciones()
                _mensaje.value = "Transacción guardada correctamente"
            } catch (e: Exception) {
                _mensaje.value = "Error al guardar transacción"
            }
        }
    }

    fun cargarUsuariosApi() {
        viewModelScope.launch {
            val resultado = repository.obtenerUsuariosApi()

            resultado
                .onSuccess {
                    _usuariosApi.value = it
                    _mensaje.value = "Datos obtenidos correctamente"
                }
                .onFailure {
                    _mensaje.value = "Error de conexión con la API"
                }
        }
    }
}
