package com.syntax.asena.model

import com.syntax.asena.error.UniversalAPIError
import javax.servlet.http.HttpServletRequest

data class Response(
        private val exception: UniversalAPIError,
        private val request: HttpServletRequest,
        val timestamp: Long = System.currentTimeMillis()
){
    val path: String = request.requestURI.toString()
    val method: String = request.method
    val status: Int = exception.status.value()
    val error: String = exception::class.java.simpleName
    val message: String = exception.status.reasonPhrase
}
