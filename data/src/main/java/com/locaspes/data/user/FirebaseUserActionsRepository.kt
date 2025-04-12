package com.locaspes.data.user

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.locaspes.data.UserDataRepository
import com.locaspes.data.model.ProjectCard
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserActionsRepository @Inject constructor(
    private val userDataRepository: UserDataRepository
): UserActionsRepository {

    private val dataBase = Firebase.firestore

    override suspend fun addApplicationToProject(projectId: String): Boolean {
        val currentUserId = userDataRepository.getUserId().first()!!
        try {
            Log.d("Debug","userid" + currentUserId + "projectId" + projectId)
            if (!checkUserAppliedToProject(projectId)) {
                dataBase.collection("Users").document(currentUserId)
                    .update("projects_applications", FieldValue.arrayUnion(projectId)).await()
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun checkUserAppliedToProject(projectId: String): Boolean{
        val currentUserId = userDataRepository.getUserId().first()!!
        val userDocument = dataBase.collection("Users").document(currentUserId).get().await()
        val userApplications = userDocument.get("projects_applications") as? List<String> ?: emptyList()
        Log.d("FirebaseUserActionsRepository", "contains project - ${userApplications.contains(projectId)} ")
        return userApplications.contains(projectId)
    }

    override suspend fun cancelUserApplication(projectId: String): Boolean {
        val currentUserId = userDataRepository.getUserId().first()!!
        try {
            if (checkUserAppliedToProject(projectId)){
                dataBase.collection("Users").document(currentUserId)
                    .update("projects_applications", FieldValue.arrayRemove(projectId)).await()
            }
            return true
        }
        catch (e: Exception){
            return false
        }
    }

    override suspend fun createProject(projectCard: ProjectCard): Boolean{
        try {
            projectCard.author = userDataRepository.getUserId().first()!!
            projectCard.createDate = Timestamp.now()
            val newProjectDocument = dataBase.collection("Projects").add(projectCard).await()
            newProjectDocument.update("id", newProjectDocument.id)
            dataBase.collection("Users")
                .document(userDataRepository.getUserId().first()!!)
                .update("projects_created", FieldValue.arrayUnion(newProjectDocument.id))
            return true
        }
        catch (e: Exception){
            return false
        }
    }
}