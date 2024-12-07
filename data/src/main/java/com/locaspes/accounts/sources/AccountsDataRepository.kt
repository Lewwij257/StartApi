package com.locaspes.accounts.sources

import com.locaspes.accounts.entities.AccountDataEntity

interface AccountsDataRepository {

    fun getAccountData()

    fun setAccountUsername()

    fun setAccountPassword()

    fun setAccountEmail()

    suspend fun signIn(usernameOrEmail:String, password:String)

    fun updateLocalDatabase()

    suspend fun signUp(newAccountData: AccountDataEntity): Boolean

    suspend fun checkIfAccountExists(username: String, email: String): Boolean

    fun getCollection(): MutableList<Map<String, Any>>

}