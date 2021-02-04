package com.syntax.asena.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "servers")
data class Server(
        @Id val id: ObjectId = ObjectId.get(),
        val prefix: String,
        val server_id: String,
        val publicCommands: MutableList<String>,
        val updatedAt: LocalDateTime = LocalDateTime.now(),
        val createdAt: LocalDateTime = LocalDateTime.now()
)