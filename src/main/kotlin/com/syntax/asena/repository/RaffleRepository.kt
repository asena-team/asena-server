package com.syntax.asena.repository

import com.syntax.asena.model.Raffle
import org.springframework.data.mongodb.repository.MongoRepository

interface RaffleRepository : MongoRepository<Raffle, String>