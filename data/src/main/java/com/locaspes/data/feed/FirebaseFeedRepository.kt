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
            val userId = userDataRepository.getUserId().first()
            Log.d("FirebaseFeedRepositoryDebug", "userId: $userId")
            val userDocumentSnapshot = usersDatabaseRef.document(userId.toString()).get().await()
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
            val projectDocumentSnapshot = projectsDatabaseRef.document(projectId).get().await()
            val appliedUserList = projectDocumentSnapshot.get("usersApplied") as? List<String> ?: emptyList()
            val acceptedUsersList = projectDocumentSnapshot.get("usersAccepted") as? List<String> ?: emptyList()
            val creatorUserList = projectDocumentSnapshot.get("author") as? List<String> ?: emptyList()

            val projectRelatedUsers = listOf(appliedUserList, acceptedUsersList, creatorUserList)

            if (projectRelatedUsers.any{it.isNotEmpty()}){
                val usersApplied = if (projectRelatedUsers[0].isNotEmpty()){
                    usersDatabaseRef.whereIn("id", projectRelatedUsers[0]).get().await()
                        .documents.mapNotNull { documentSnapshot ->
                            documentSnapshot.toObject(UserProfile::class.java)?.copy(id = documentSnapshot.id)
                        }
                } else emptyList()

                val userAccepted = if (projectRelatedUsers[1].isNotEmpty()){
                    usersDatabaseRef.whereIn("id", projectRelatedUsers[1]).get().await()
                        .documents.mapNotNull { documentSnapshot ->
                            documentSnapshot.toObject(UserProfile::class.java)?.copy(id = documentSnapshot.id)
                        }
                } else emptyList()

                val userCreatorList = if (projectRelatedUsers[2].isNotEmpty()){
                    usersDatabaseRef.whereIn("id", projectRelatedUsers[2]).get().await()
                        .documents.mapNotNull { documentSnapshot ->
                            documentSnapshot.toObject(UserProfile::class.java)?.copy(id = documentSnapshot.id)
                        }
                } else emptyList()

                val users = listOf(usersApplied, userAccepted, userCreatorList)
                Log.d("getProjectRelatedUsers", "project related users: $users")
                Result.success(users)
            }
            else
                Result.success(emptyList())
        }
        catch (e: Exception){
            Result.failure(Exception("Не удалось загрузить : ${e.message}"))
        }
    }



}