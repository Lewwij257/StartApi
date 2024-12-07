package com.locaspes.local

import android.content.Context

class UserSharedPreferences(context: Context) {

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
    }

    private val sharedPreferences = context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)

    fun saveLoginData(username: String, email: String, password: String){
        with(sharedPreferences.edit()){
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_USERNAME, username)
            putString(KEY_EMAIL, email)
            putString(KEY_PASSWORD, password)
            apply()
        }
    }

    fun clearLoginData() {
        with(sharedPreferences.edit()) {
            putBoolean(KEY_IS_LOGGED_IN, false)
            remove(KEY_USERNAME)
            remove(KEY_EMAIL)
            remove(KEY_PASSWORD)
            apply()
        }
    }

    fun getUserId(){
        //TODO
    }

    fun getUsername():String?{
        return sharedPreferences.getString(KEY_USERNAME, null)
    }
    fun getEmail():String?{
        return sharedPreferences.getString(KEY_EMAIL, null)
    }
    fun getPassword():String?{
        return sharedPreferences.getString(KEY_PASSWORD, null)
    }

}