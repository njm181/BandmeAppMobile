package com.bandme.bandmeappmobile.domain.utils

import android.content.Context
import android.preference.PreferenceManager

const val AUTHORIZATION = "authorizacion"

class AppPreferences(context: Context) {

    private val sharedPreferences =
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)

    fun saveAuthorization(token: String) =
        sharedPreferences.edit().putString(AUTHORIZATION,token).apply()

    fun getAuthorization(): String = sharedPreferences.getString(AUTHORIZATION, "") ?: ""
}