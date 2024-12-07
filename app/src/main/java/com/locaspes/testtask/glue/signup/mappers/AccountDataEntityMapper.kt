package com.locaspes.testtask.glue.signup.mappers

import com.locaspes.accounts.entities.AccountDataEntity
import com.locaspes.domain.entities.SignUpData

class AccountDataEntityMapper {

    fun toAccountDataEntity(signUpData: SignUpData): AccountDataEntity{
        return AccountDataEntity(
            signUpData.username,
            signUpData.email,
            signUpData.password,
            signUpData.premium,
            signUpData.premiumStarted,
            signUpData.projectAmount)
    }

}