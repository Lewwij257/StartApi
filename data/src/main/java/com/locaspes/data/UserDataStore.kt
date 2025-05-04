package com.locaspes.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.locaspes.data.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    companion object{
        private val USER_PROFILE_KEY = stringPreferencesKey("user")
        val gson = Gson()
    }

    val userProfile: Flow<UserProfile?> = dataStore.data.map {preferences ->
        preferences[USER_PROFILE_KEY]?.let { jsonString ->
            gson.fromJson(jsonString, UserProfile::class.java)
        }
    }

    suspend fun saveUserProfile(userProfile: UserProfile){
        dataStore.edit { preferences ->
            preferences[USER_PROFILE_KEY] = gson.toJson(userProfile)
        }
    }

    suspend fun clearUserProfile(){
        dataStore.edit { preferences ->
            preferences.remove(USER_PROFILE_KEY)
        }
    }

}