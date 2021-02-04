package com.syntax.asena.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "raffles")
data class Raffle(
        @Id val id: ObjectId = ObjectId.get(),
        val prize: String,
        val server_id: String,
        val constituent_id: String,
        val channel_id: String,
        val message_id: String,
        val numberOfWinners: Int,
        val status: Status,
        val finishAt: LocalDateTime = LocalDateTime.now(),
        val updatedAt: LocalDateTime = LocalDateTime.now(),
        val createdAt: LocalDateTime = LocalDateTime.now()
){
    enum class Status{
        FINISHED,
        ALMOST_DONE,
        CONTINUES,
        CANCELED
    }
}