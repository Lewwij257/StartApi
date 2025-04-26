package com.locaspes.data.model

import com.google.firebase.Timestamp

data class Message(
    var id: String = "", // ID документа сообщения
    val senderProfileId: String = "",
    val message: String = "",
    val projectId: String = "",
    val date: Timestamp = Timestamp.now(),
    val senderProfileName: String = ""
    )