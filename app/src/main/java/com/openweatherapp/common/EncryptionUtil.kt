package com.openweatherapp.common

import android.content.Context
import android.util.Base64
import net.sqlcipher.database.SupportFactory
import java.security.SecureRandom

object EncryptionUtil {

    private fun generateRandomPassphrase(): ByteArray {
        val secureRandom = SecureRandom()
        val passphraseBytes = ByteArray(32) // 256-bit passphrase
        secureRandom.nextBytes(passphraseBytes)
        return passphraseBytes
    }

    fun createSupportFactory(context: Context): SupportFactory {
        val cryptoManager = CryptoManager()
        val sharedPref = EncryptedSharedPrefUtil(context).getEncryptedSharedPreferences()
        if (!sharedPref.contains(Constants.ENCRYPTED_BYTE_PREF)) {
            cryptoManager.encryptPassphrase(generateRandomPassphrase(), context)
        }

        val decryptedKey = cryptoManager.decryptPassphrase(context)

        return SupportFactory(decryptedKey)
    }

}