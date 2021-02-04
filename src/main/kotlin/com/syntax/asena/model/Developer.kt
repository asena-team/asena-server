package com.syntax.asena.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "developers")
data class Developer(
        @Id val id: ObjectId = ObjectId.get(),
        val username: String,
        var password: String
)