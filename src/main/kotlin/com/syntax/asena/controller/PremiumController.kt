package com.syntax.asena.controller

import com.syntax.asena.Build
import com.syntax.asena.context.PremiumStatus
import com.syntax.asena.context.PremiumType
import com.syntax.asena.error.UniversalAPIError
import com.syntax.asena.form.PremiumForm
import com.syntax.asena.model.Response
import com.syntax.asena.model.Premium
import com.syntax.asena.service.context.PremiumService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@Component
@RestController
@RequestMapping("/api/v${Build.VersionCodes.CUR_DEVELOPMENT}/premium")
class PremiumController{

    @Autowired
    private final lateinit var premiumService: PremiumService

    @Autowired
    private final lateinit var mongoTemplate: MongoTemplate

    @GetMapping("/{id}")
    fun getPremium(@PathVariable("id") id: String): Premium? =
            premiumService.findById(id).orElseGet{
                throw UniversalAPIError(HttpStatus.NOT_FOUND)
            }

    @GetMapping("/all")
    fun getPremiums(): List<Premium> = premiumService.findAll()

    @GetMapping("/all/continues")
    fun getAllContinuesPremiums(): List<Premium> =
        mongoTemplate.find(Query().apply{
            addCriteria(Criteria.where("status").`in`(PremiumStatus.CONTINUES.toInt()))
        }, Premium::class.java)

    @PostMapping("/{id}/update")
    fun editPremium(
            @PathVariable("id") id: String,
            @RequestBody form: PremiumForm,
            request: HttpServletRequest
    ): ResponseEntity<Response>{
        val premium = premiumService.findById(id).orElseGet{
            throw UniversalAPIError(HttpStatus.NOT_FOUND)
        }

        form.id = id

        if(form.startAt >= form.finishAt){
            throw UniversalAPIError(HttpStatus.PRECONDITION_FAILED)
        }

        if(form.type == PremiumType.PERMANENT.toInt()){
            form.finishAt = LocalDateTime.of(2099, 12, 31, 23, 59, 59, 0)
        }

        premiumService.save(form.toPremium(premium.server_id))
        return ResponseEntity.accepted().body(
                Response(
                        UniversalAPIError(HttpStatus.OK),
                        request
                )
        )
    }

}