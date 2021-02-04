package com.syntax.asena.scheduler

import com.syntax.asena.context.PremiumStatus
import com.syntax.asena.context.PremiumType
import com.syntax.asena.model.Premium
import com.syntax.asena.service.context.PremiumService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/** TODO::Move these functions to GCP Cloud Scheduler */
@Component
class PremiumTimeCheckerTask{

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Autowired
    private lateinit var premiumService: PremiumService

    @Scheduled(cron = "0 * * * * *", zone = "Europe/Istanbul")
    fun onRun(){
        val expiredPremiums = mongoTemplate.find(
                Query().apply{
                    addCriteria(Criteria.where("status").`in`(PremiumStatus.CONTINUES.toInt()))
                    addCriteria(Criteria.where("type").`in`(PremiumType.LIMITED.toInt()))
                    addCriteria(Criteria.where("finishAt").lte(LocalDateTime.now()))
                },
                Premium::class.java
        )

        if(expiredPremiums.size > 0){
            expiredPremiums.map{
                it.status = PremiumStatus.FINISHED.toInt()

                premiumService.save(it)
            }
        }
    }

}