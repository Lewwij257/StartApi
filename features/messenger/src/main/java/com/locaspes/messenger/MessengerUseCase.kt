package com.locaspes.messenger

import com.locaspes.data.UserDataRepository
import com.locaspes.data.model.ChatItem
import com.locaspes.data.model.Message
import com.locaspes.data.model.UserProfile
import com.locaspes.data.user.FirebaseUserActionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MessengerUseCase @Inject constructor(
    private val firebaseUserActionsRepository: FirebaseUserActionsRepository,
    private val userDataRepository: UserDataRepository
) {

    suspend fun getChatMessages(projectId: String): Flow<Result<List<Message>>> {
        return firebaseUserActionsRepository.getChatMessages(projectId)
            .map { result ->
                if (result.isSuccess) {
                    Result.success(result.getOrNull()?.sortedBy { it.date } ?: emptyList())
                } else {
                    Result.failure(result.exceptionOrNull() ?: Exception("Неизвестная ошибка"))
                }
            }
    }

    suspend fun getUserId(): String{
        return userDataRepository.getUserId().first()!!
    }

    suspend fun getUserNickname(): String{
        return userDataRepository.getUserName().first()!!
    }

    suspend fun getChats(): Result<List<ChatItem>>{
        return firebaseUserActionsRepository.getUserChats()
    }

    suspend fun loadUserProfile(userId: String): Result<UserProfile> {
        return firebaseUserActionsRepository.getUserProfile(userId)
    }

    suspend fun sendMessage(message: Message): Result<String>{
        return firebaseUserActionsRepository.sendMessage(message)
    }

}