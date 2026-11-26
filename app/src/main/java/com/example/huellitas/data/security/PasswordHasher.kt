package com.example.huellitas.data.security

import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object PasswordHasher {
    private const val ITERACIONES = 120_000
    private const val BITS_HASH = 256
    private const val BYTES_SAL = 16

    fun hash(contrasena: String): String {
        val sal = ByteArray(BYTES_SAL).also { SecureRandom().nextBytes(it) }
        return "${codificar(sal)}:${codificar(derivar(contrasena, sal))}"
    }

    fun verificar(contrasena: String, guardado: String): Boolean {
        val partes = guardado.split(":")
        if (partes.size != 2) return false
        val sal = Base64.getDecoder().decode(partes[0])
        val esperado = Base64.getDecoder().decode(partes[1])
        return MessageDigest.isEqual(esperado, derivar(contrasena, sal))
    }

    private fun derivar(contrasena: String, sal: ByteArray): ByteArray {
        val spec = PBEKeySpec(contrasena.toCharArray(), sal, ITERACIONES, BITS_HASH)
        return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).encoded
    }

    private fun codificar(bytes: ByteArray): String = Base64.getEncoder().encodeToString(bytes)
}
