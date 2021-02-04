package com.syntax.asena.service

import com.syntax.asena.context.PremiumStatus
import com.syntax.asena.model.Premium
import com.syntax.asena.service.context.ServerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class ServerServiceImpl : ServerService{

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    override fun findActivePremiumById(server_id: String): Premium?{
        val query = Query()
                .addCriteria(Criteria.where("server_id").`is`(server_id))
                .addCriteria(Criteria.where("status").`is`(PremiumStatus.CONTINUES.toInt()))

        return mongoTemplate.findOne(query, Premium::class.java)
    }

}