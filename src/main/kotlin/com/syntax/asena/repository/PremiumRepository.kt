package com.syntax.asena.repository

import com.syntax.asena.model.Premium
import org.springframework.data.mongodb.repository.MongoRepository

interface PremiumRepository : MongoRepository<Premium, String>