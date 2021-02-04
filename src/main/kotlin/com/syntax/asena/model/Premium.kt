package com.syntax.asena.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.time.LocalDateTime

@Document(collection = "premiums")
data class Premium(
        @Id
        var id: ObjectId = ObjectId.get(),
        val server_id: String,
        var status: Int,
        var type: Int,
        var startAt: LocalDateTime = LocalDateTime.now(),
        var finishAt: LocalDateTime
): Serializable