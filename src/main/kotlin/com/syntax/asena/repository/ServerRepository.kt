package com.syntax.asena.repository

import com.syntax.asena.model.Server
import com.syntax.asena.service.context.ServerService
import org.springframework.data.mongodb.repository.MongoRepository

interface ServerRepository : MongoRepository<Server, String>