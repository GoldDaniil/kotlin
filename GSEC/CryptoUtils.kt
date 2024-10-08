package com.example.goldsecure.utils

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import android.util.Base64

object CryptoUtils {
    private const val ALGORITHM = "AES"

    private fun generateKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM)
        keyGenerator.init(256) // 256-bit AES
        return keyGenerator.generateKey()
    }

    private fun getKeyFromString(keyString: String): SecretKey {
        val decodedKey = Base64.decode(keyString, Base64.DEFAULT)
        return SecretKeySpec(decodedKey, 0, decodedKey.size, ALGORITHM)
    }

    fun encrypt(data: String, keyString: String): String {
        val key = getKeyFromString(keyString)
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    fun decrypt(encryptedData: String, keyString: String): String {
        val key = getKeyFromString(keyString)
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decoded = Base64.decode(encryptedData, Base64.DEFAULT)
        val decrypted = cipher.doFinal(decoded)
        return String(decrypted, Charsets.UTF_8)
    }

    fun getKeyString(): String {
        val key = generateKey()
        return Base64.encodeToString(key.encoded, Base64.DEFAULT)
    }
}
