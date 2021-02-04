package com.syntax.asena.service.context

import com.syntax.asena.model.Premium

interface ServerService{

    fun findActivePremiumById(server_id: String): Premium?

}