package com.locaspes.data.feed

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.locaspes.data.UserDataRepository
import com.locaspes.data.model.ProjectCard
import com.locaspes.data.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseFeedRepository @Inject constructor(
    private val userDataRepository: UserDataRepository) : FeedRepository {

    val querySize = 10

    private val projectsDatabaseRef = FirebaseFirestore.getInstance().collection("Projects")
    private val usersDatabaseRef = FirebaseFirestore.getInstance().collection("Users")

    override fun getAllProjects(): Flow<List<ProjectCard>> = flow {
        try{
            val snapshot = projectsDatabaseRef.get().await()
            val projects = snapshot.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject(ProjectCard::class.java)?.copy(id = documentSnapshot.id)
            }
            emit(projects)
        }
        catch (e: Exception){
            throw e
        }
    }

    override fun getUserRelatedProjects(): Flow<List<List<ProjectCard>>> = flow {
        try {
            val userId = userDataRepository.getUserProfile().first()!!.id
            Log.d("FirebaseFeedRepositoryDebug", "userId: $userId")
            val userDocumentSnapshot = usersDatabaseRef.document(userId).get().await()
            val createdProjectsList = userDocumentSnapshot.get("projectsCreated") as? List<String> ?: emptyList()
            Log.d("FirebaseFeedRepositoryDebug", "Created projects: $createdProjectsList")
            val acceptedProjectsList = userDocumentSnapshot.get("projectsAccepted") as? List<String> ?: emptyList()
            Log.d("FirebaseFeedRepositoryDebug", "accepted projects: $acceptedProjectsList")
            val applicationsProjectsList = userDocumentSnapshot.get("projectsApplications") as? List<String> ?: emptyList()
            Log.d("FirebaseFeedRepositoryDebug", "applied projects: $applicationsProjectsList")

            //only ID's in this list
            val userRelatedProjects = listOf(createdProjectsList, acceptedProjectsList, applicationsProjectsList)

            if (userRelatedProjects.any{it.isNotEmpty()}){
                val projectsCreated = if (userRelatedProjects[0].isNotEmpty()){
                    projectsDatabaseRef.whereIn("id", userRelatedProjects[0]).get().await()
                        .documents.mapNotNull { documentSnapshot ->
                            documentSnapshot.toObject(ProjectCard::class.java)?.copy(id = documentSnapshot.id)
                        }
                } else emptyList()

                val projectsAccepted = if (userRelatedProjects[1].isNotEmpty()){
                    projectsDatabaseRef.whereIn("id", userRelatedProjects[1]).get().await()
                        .documents.mapNotNull { documentSnapshot ->
                            documentSnapshot.toObject(ProjectCard::class.java)?.copy(id = documentSnapshot.id)
                        }
                } else emptyList()

                val projectsApplied = if (userRelatedProjects[2].isNotEmpty()){
                    projectsDatabaseRef.whereIn("id", userRelatedProjects[2]).get().await()
                        .documents.mapNotNull { documentSnapshot ->
                            documentSnapshot.toObject(ProjectCard::class.java)?.copy(id = documentSnapshot.id)
                        }
                } else emptyList()

                val projects = listOf(projectsCreated, projectsAccepted, projectsApplied)
                Log.d("FirebaseFeedRepositoryDebug", "projects: $projects")
                emit(projects)
        }
            else
                emit(emptyList())
    }
        catch (e: Exception){
            Log.d("FirebaseFeedRepository", "getUserRelatedProjects error")
        }
    }

    override fun getProjectsPaginated(lastDocument: DocumentSnapshot?): Flow<List<ProjectCard>> = flow {
        try {
            val query = if (lastDocument == null){
                projectsDatabaseRef.orderBy("createDate").limit(querySize.toLong())
            }
            else{
                projectsDatabaseRef.orderBy("createDate").startAfter(lastDocument).limit(querySize.toLong())
            }
            Log.d("FirebaseFeedRepository", "Executing query, lastDocument: $lastDocument")
            val snapshot = query.get().await()
            Log.d("FirebaseFeedRepository", "Query completed, documents received: ${snapshot.documents.size}")
            val projects = snapshot.documents.mapNotNull { documentSnapshot ->
                try {
                    val project = documentSnapshot.toObject(ProjectCard::class.java)
                    Log.d("FirebaseFeedRepository", "Raw project for ${documentSnapshot.id}: $project")
                    project?.copy(id = documentSnapshot.id).also {
                        Log.d("FirebaseFeedRepository", "Converted project for ${documentSnapshot.id}: $it")
                    }
                } catch (e: Exception) {
                    Log.e("FirebaseFeedRepository", "Error converting document ${documentSnapshot.id}: $e")
                    null
                }
            }
            Log.d("FirebaseFeedRepository", "Total projects after conversion: ${projects.size}")

            emit(projects)
        }
        catch (e: Exception){
            throw e
        }
    }

    override fun getLastDocument(
        projects: List<ProjectCard>,
        documents: List<DocumentSnapshot>
    ): DocumentSnapshot? {
        if (projects.isEmpty()){
            return null
        }
        val lastProjectIndex = projects.size-1
        return documents.getOrNull(lastProjectIndex)
    }

    suspend fun getDocuments(lastDocument: DocumentSnapshot?): List<DocumentSnapshot> {
        val query = if (lastDocument == null) {
            projectsDatabaseRef.orderBy("createDate").limit(querySize.toLong())
        } else {
            projectsDatabaseRef.orderBy("createDate").startAfter(lastDocument).limit(querySize.toLong())
        }
        return query.get().await().documents
    }

    override suspend fun getProjectRelatedUsers(projectId: String): Result<List<List<UserProfile>>> {
        return try {
            Log.d("ProjectUsers", "Starting getProjectRelatedUsers for projectId: $projectId")

            // 1. Получаем документ проекта
            Log.d("ProjectUsers", "Fetching project document...")
            val projectDocumentSnapshot = projectsDatabaseRef.document(projectId).get().await()
            Log.d("ProjectUsers", "Project document snapshot exists: ${projectDocumentSnapshot.exists()}")
            Log.d("ProjectUsers", "Full project data: ${projectDocumentSnapshot.data}")
            // 2. Извлекаем списки пользователей
            val appliedUserList = projectDocumentSnapshot.get("usersApplied") as? List<String> ?: emptyList()
            val acceptedUsersList = projectDocumentSnapshot.get("usersAccepted") as? List<String> ?: emptyList()
            val creatorUserList = listOf(projectDocumentSnapshot.get("author") as? String ?: "").filter { it.isNotEmpty() }

            Log.d("ProjectUsers", "Applied users IDs: $appliedUserList")
            Log.d("ProjectUsers", "Accepted users IDs: $acceptedUsersList")
            Log.d("ProjectUsers", "Creator user ID: $creatorUserList")

            val projectRelatedUsers = listOf(appliedUserList, acceptedUsersList, creatorUserList)
            Log.d("ProjectUsers", "All user lists combined: $projectRelatedUsers")

            // 3. Проверяем, есть ли вообще пользователи
            if (projectRelatedUsers.any { it.isNotEmpty() }) {
                Log.d("ProjectUsers", "Found non-empty user lists, proceeding to fetch user details")

                // 4. Запрашиваем данные пользователей
                val usersApplied = if (projectRelatedUsers[0].isNotEmpty()) {
                    Log.d("ProjectUsers", "Fetching applied users...")
                    val querySnapshot = usersDatabaseRef.whereIn("id", projectRelatedUsers[0]).get().await()
                    Log.d("ProjectUsers", "Applied users query result count: ${querySnapshot.documents.size}")
                    querySnapshot.documents.mapNotNull { documentSnapshot ->
                        documentSnapshot.toObject(UserProfile::class.java)?.copy(id = documentSnapshot.id).also {
                            Log.d("ProjectUsers", "Applied user loaded: ${documentSnapshot.id}")
                        }
                    }
                } else {
                    Log.d("ProjectUsers", "No applied users to fetch")
                    emptyList()
                }

                val userAccepted = if (projectRelatedUsers[1].isNotEmpty()) {
                    Log.d("ProjectUsers", "Fetching accepted users...")
                    val querySnapshot = usersDatabaseRef.whereIn("id", projectRelatedUsers[1]).get().await()
                    Log.d("ProjectUsers", "Accepted users query result count: ${querySnapshot.documents.size}")
                    querySnapshot.documents.mapNotNull { documentSnapshot ->
                        documentSnapshot.toObject(UserProfile::class.java)?.copy(id = documentSnapshot.id).also {
                            Log.d("ProjectUsers", "Accepted user loaded: ${documentSnapshot.id}")
                        }
                    }
                } else {
                    Log.d("ProjectUsers", "No accepted users to fetch")
                    emptyList()
                }

                val userCreatorList = if (projectRelatedUsers[2].isNotEmpty()) {
                    Log.d("ProjectUsers", "Fetching creator user...")
                    val querySnapshot = usersDatabaseRef.whereIn("id", projectRelatedUsers[2]).get().await()
                    Log.d("ProjectUsers", "Creator user query result count: ${querySnapshot.documents.size}")
                    querySnapshot.documents.mapNotNull { documentSnapshot ->
                        documentSnapshot.toObject(UserProfile::class.java)?.copy(id = documentSnapshot.id).also {
                            Log.d("ProjectUsers", "Creator user loaded: ${documentSnapshot.id}")
                        }
                    }
                } else {
                    Log.d("ProjectUsers", "No creator user to fetch")
                    emptyList()
                }

                val users = listOf(usersApplied, userAccepted, userCreatorList)
                Log.d("ProjectUsers", "Final result - Applied: ${usersApplied.size}, Accepted: ${userAccepted.size}, Creator: ${userCreatorList.size}")
                Result.success(users)
            } else {
                Log.d("ProjectUsers", "All user lists are empty")
                Result.success(emptyList())
            }
        } catch (e: Exception) {
            Log.e("ProjectUsers", "Error in getProjectRelatedUsers: ${e.message}", e)
            Result.failure(Exception("Не удалось загрузить пользователей: ${e.message}"))
        }
    }



}