package com.syntax.asena.service

import com.syntax.asena.context.Queues
import com.syntax.asena.model.Premium
import com.syntax.asena.repository.PremiumRepository
import com.syntax.asena.service.context.PremiumService
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PremiumServiceImpl : PremiumService{

    @Autowired
    private lateinit var rabbitTemplate: RabbitTemplate

    @Autowired
    private lateinit var premiumRepository: PremiumRepository

    override fun queuePremiumMessage(premium: Premium){
        rabbitTemplate.convertAndSend(Queues.PREMIUM.bindingName, premium)
    }

    override fun save(premium: Premium): Premium{
        val save = premiumRepository.save(premium)
        queuePremiumMessage(save)

        return save
    }

    override fun findById(id: String): Optional<Premium>{
        return premiumRepository.findById(id)
    }

    override fun findAll(): List<Premium>{
        return premiumRepository.findAll()
    }

}