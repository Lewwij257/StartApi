package com.locaspes.accounts.entities

data class AccountDataEntity(
    val username: String = "", val email: String = "", val password: String = "",
    val premium: Boolean = false, val premiumStarted: String = "",
    val projectAmount: Int = 0
)