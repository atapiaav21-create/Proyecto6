package com.example.proyecto6.data.local

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class SecurityUtilsTest {

    @Test
    fun mismaContraseñaGeneraMismoHash() {
        val hash1 = SecurityUtils.hashPassword("123456")
        val hash2 = SecurityUtils.hashPassword("123456")

        assertEquals(hash1, hash2)
    }

    @Test
    fun contraseñasDiferentesGeneranHashesDiferentes() {
        val hash1 = SecurityUtils.hashPassword("123456")
        val hash2 = SecurityUtils.hashPassword("654321")

        assertNotEquals(hash1, hash2)
    }
}
