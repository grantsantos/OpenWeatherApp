package com.openweatherapp.common

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class EncryptedSharedPrefUtil(
    context: Context
) {
    private var encryptedSharePreferences: SharedPreferences

    init {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        encryptedSharePreferences = EncryptedSharedPreferences.create(
            Constants.ENCRYPTED_SHARED_PREF,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getEncryptedSharedPreferences() : SharedPreferences {
        return encryptedSharePreferences
    }

}