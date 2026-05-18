package com.example.chat

import com.locaspes.data.UserDataRepository
import com.locaspes.data.user.FirebaseUserActionsRepository
import com.locaspes.model.Message
import com.locaspes.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatUseCase @Inject constructor(
    private val firebaseUserActionsRepository: FirebaseUserActionsRepository,
    private val userDataRepository: UserDataRepository
){
    suspend fun sendMessage(message: Message): Result<String>{
        return firebaseUserActionsRepository.sendMessage(message)
    }

    suspend fun getUserProfile(): Result<UserProfile> {
        return firebaseUserActionsRepository.getUserProfile(userDataRepository.getUserProfile().first()!!.id)
    }

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
}