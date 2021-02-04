package com.syntax.asena.service.context

import com.syntax.asena.model.Premium
import java.util.*

interface PremiumService{

    fun queuePremiumMessage(premium: Premium)

    fun save(premium: Premium): Premium

    fun findById(id: String): Optional<Premium>

    fun findAll(): List<Premium>

}