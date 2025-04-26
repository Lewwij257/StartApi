package com.locaspes.messenger

import com.locaspes.data.model.ChatItem
import com.locaspes.data.model.Message
import com.locaspes.data.user.FirebaseUserActionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MessengerUseCase @Inject constructor(
    private val firebaseUserActionsRepository: FirebaseUserActionsRepository
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

    suspend fun getChats(): Result<List<ChatItem>>{
        return firebaseUserActionsRepository.getUserChats()
    }
}