package com.locaspes.data.user

import com.locaspes.data.UserDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalUserActions @Inject constructor(private val userDataStore: UserDataStore){
}