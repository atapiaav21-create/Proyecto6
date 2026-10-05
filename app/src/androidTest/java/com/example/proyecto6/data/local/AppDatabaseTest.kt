package com.example.proyecto6.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var database: AppDatabase

    @Before
    fun crearBaseDeDatos() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun cerrarBaseDeDatos() {
        database.close()
    }

    @Test
    fun insertarYObtenerUsuario() = runBlocking {
        val usuario = Usuario(
            nombre = "Usuario Test",
            email = "test@alke.cl",
            passwordHash = "hash"
        )

        database.usuarioDao().insertar(usuario)

        val usuarioGuardado =
            database.usuarioDao().obtenerPorEmail("test@alke.cl")

        assertNotNull(usuarioGuardado)
        assertEquals("Usuario Test", usuarioGuardado?.nombre)
        assertEquals("test@alke.cl", usuarioGuardado?.email)
    }
}
