package com.syntax.asena.controller

import com.syntax.asena.Build
import com.syntax.asena.context.PremiumStatus
import com.syntax.asena.error.UniversalAPIError
import com.syntax.asena.form.PremiumForm
import com.syntax.asena.model.Response
import com.syntax.asena.model.Server
import com.syntax.asena.repository.ServerRepository
import com.syntax.asena.service.context.PremiumService
import com.syntax.asena.service.context.ServerService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@Component
@RestController
@RequestMapping("/api/v${Build.VersionCodes.CUR_DEVELOPMENT}/server")
class ServerController{

    @Autowired
    private final lateinit var serverRepository: ServerRepository

    @Autowired
    private final lateinit var serverService: ServerService

    @Autowired
    private final lateinit var premiumService: PremiumService

    @Autowired
    private final lateinit var mongoTemplate: MongoTemplate

    @GetMapping("/{id}")
    fun getServer(@PathVariable("id") id: String): Server?{
        return if(ObjectId.isValid(id)){
            serverRepository.findById(id).orElseGet{
                throw UniversalAPIError(HttpStatus.NOT_FOUND)
            }
        }else{
            val query = Query().addCriteria(Criteria.where("server_id").`is`(id))

            mongoTemplate.findOne(query, Server::class.java) ?: throw UniversalAPIError(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/{id}/premium")
    fun setServerPremium(
            @PathVariable("id") id: String,
            @RequestBody form: PremiumForm,
            request: HttpServletRequest
    ): ResponseEntity<Response>{
        val server = serverRepository.findById(id).orElseGet{
            throw UniversalAPIError(HttpStatus.NOT_FOUND)
        }

        val search = serverService.findActivePremiumById(server.server_id)
        if(search !== null){
            throw UniversalAPIError(HttpStatus.PRECONDITION_FAILED)
        }

        premiumService.save(form.toPremium(server.server_id))

        return ResponseEntity.accepted().body(
                Response(
                        UniversalAPIError(HttpStatus.OK),
                        request
                )
        )
    }

    @DeleteMapping("/{id}/premium")
    fun cancelServerPremium(
            @PathVariable("id") id: String,
            request: HttpServletRequest
    ): ResponseEntity<Response>{
        val server = serverRepository.findById(id).orElseGet{
            throw UniversalAPIError(HttpStatus.NOT_FOUND)
        }

        val search = serverService.findActivePremiumById(server.server_id)
        if(search === null){
            throw UniversalAPIError(HttpStatus.PRECONDITION_FAILED)
        }

        search.status = PremiumStatus.CANCELED.toInt()

        premiumService.save(search)
        return ResponseEntity.accepted().body(
                Response(
                        UniversalAPIError(HttpStatus.OK),
                        request
                )
        )
    }

}