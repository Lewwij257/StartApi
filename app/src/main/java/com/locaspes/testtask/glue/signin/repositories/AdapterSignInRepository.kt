package com.locaspes.testtask.glue.signin.repositories

import com.locaspes.accounts.AccountsDataRepository
import com.locaspes.domain.repositories.SignInRepository
import javax.inject.Inject

class AdapterSignInRepository @Inject constructor(private val accountsDataRepository: AccountsDataRepository) : SignInRepository {

    override fun signIn() {
        return accountsDataRepository.signIn()
    }
}