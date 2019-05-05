package com.arctouch.codechallenge.preference

import android.content.Context
import android.preference.PreferenceManager

object MainPreference {

    private const val USER_QUERY = "userQuery"

    fun setUserReference(context: Context, userQuery: String) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putString(USER_QUERY, userQuery)
        editor.apply()
    }

    fun getUserReference(context: Context): String? {
        return try {
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            sharedPref.getString(USER_QUERY, null)
        } catch (e: NullPointerException) {
            ""
        }
    }
}