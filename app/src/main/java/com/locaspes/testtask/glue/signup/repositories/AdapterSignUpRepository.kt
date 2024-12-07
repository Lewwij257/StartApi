package com.locaspes.testtask.glue.signup.repositories

import com.locaspes.accounts.AccountsDataRepository
import com.locaspes.accounts.entities.AccountDataEntity
import com.locaspes.domain.entities.SignUpData
import com.locaspes.domain.repositories.SignUpRepository
import com.locaspes.testtask.glue.signup.mappers.AccountDataEntityMapper
import javax.inject.Inject

class AdapterSignUpRepository @Inject constructor(
    private val accountDataRepository: AccountsDataRepository
): SignUpRepository{

    override suspend fun signUp(newAccountData: SignUpData): Boolean {
        return accountDataRepository.signUp(AccountDataEntityMapper().toAccountDataEntity(newAccountData))
    }

    override fun getCollection():  MutableList<Map<String, Any>>{
        return accountDataRepository.getCollection()
    }

}