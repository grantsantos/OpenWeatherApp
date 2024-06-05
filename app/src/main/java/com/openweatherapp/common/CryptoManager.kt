package com.openweatherapp.common

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class CryptoManager {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private val encryptCipher = Cipher.getInstance(TRANSFORMATION).apply {
        init(Cipher.ENCRYPT_MODE, getKey())
    }

    private fun getDecryptCipherForIv(iv: ByteArray) : Cipher {
        return Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        }
    }

    private fun getKey() : SecretKey {
        val existingKey = keyStore.getEntry("secret", null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey() : SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    "secret",
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    fun encryptPassphrase(byteArray: ByteArray, context: Context) : ByteArray {
        val encryptedBytes = encryptCipher.doFinal(byteArray)
        val encryptedSharedPreferences = EncryptedSharedPrefUtil(context).getEncryptedSharedPreferences()

        //save iv
        //convert to base64 string first
        val iv = Base64.encodeToString(encryptCipher.iv, Base64.DEFAULT)
        encryptedSharedPreferences
            .edit()
            .putString(Constants.IV_PREF, iv)
            .apply()

        //save encrypted bytes
        //convert to base64 string first
        val eb = Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        encryptedSharedPreferences
            .edit()
            .putString(Constants.ENCRYPTED_BYTE_PREF, eb)
            .apply()

        return encryptedBytes
    }

    fun decryptPassphrase(context: Context) : ByteArray {
        val encryptedSharedPreferences = EncryptedSharedPrefUtil(context).getEncryptedSharedPreferences()

        //retrieve iv size
        //val ivSize = encryptedSharedPreferences.getInt(Constants.IV_SIZE_PREF, 0)

        //retrieve iv
        val ivString = encryptedSharedPreferences.getString(Constants.IV_PREF, "") ?: ""
        val iv = Base64.decode(ivString, Base64.DEFAULT)

        //retrieve encrypted bytes size
        //val encryptedBytesSize = encryptedSharedPreferences.getInt(Constants.ENCRYPTED_BYTE_SIZE_PREF, 0)

        //retrieve encrypted bytes
        val encryptedString = encryptedSharedPreferences.getString(Constants.ENCRYPTED_BYTE_PREF, "")?.toByteArray() ?: byteArrayOf()
        val encryptedBytes = Base64.decode(encryptedString, Base64.DEFAULT)

        return getDecryptCipherForIv(iv).doFinal(encryptedBytes)
    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }

}