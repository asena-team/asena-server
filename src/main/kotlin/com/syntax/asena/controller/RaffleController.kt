package com.syntax.asena.controller

import com.syntax.asena.Build
import com.syntax.asena.error.UniversalAPIError
import com.syntax.asena.model.Raffle
import com.syntax.asena.repository.RaffleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@Component
@RestController
@RequestMapping("/api/v${Build.VersionCodes.CUR_DEVELOPMENT}/raffle")
class RaffleController{

    @Autowired
    private final lateinit var repository: RaffleRepository

    @Autowired
    private final lateinit var mongoTemplate: MongoTemplate

    @GetMapping("/{id}")
    fun getRaffle(@PathVariable("id") id: String, response: HttpServletResponse): Raffle? =
            repository.findById(id).orElseGet{
                throw UniversalAPIError(HttpStatus.NOT_FOUND)
            }

    @GetMapping("/all/{id}")
    fun getRaffles(@PathVariable("id") id: String): List<Raffle>{
        val query = Query().addCriteria(Criteria.where("server_id").`is`(id))

        return mongoTemplate.find(query, Raffle::class.java)
    }

}