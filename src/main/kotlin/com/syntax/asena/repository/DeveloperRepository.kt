package com.syntax.asena.repository

import com.syntax.asena.model.Developer
import org.springframework.data.mongodb.repository.MongoRepository

interface DeveloperRepository : MongoRepository<Developer, String>{

    fun findByUsername(username: String): Developer?

}