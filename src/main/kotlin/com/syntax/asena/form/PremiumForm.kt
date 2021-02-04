package com.syntax.asena.form

import com.syntax.asena.context.PremiumStatus
import com.syntax.asena.model.Premium
import org.bson.types.ObjectId
import java.time.LocalDateTime

data class PremiumForm(
        var id: String? = null,
        var type: Int,
        var startAt: LocalDateTime,
        var finishAt: LocalDateTime
){

    fun toPremium(server_id: String): Premium = Premium(
            if(id === null || !ObjectId.isValid(id)) ObjectId.get() else ObjectId(id),
            server_id,
            PremiumStatus.CONTINUES.toInt(),
            type,
            startAt,
            finishAt
    )

}