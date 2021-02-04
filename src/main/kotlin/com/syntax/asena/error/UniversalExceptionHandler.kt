package com.syntax.asena.error

import com.syntax.asena.model.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class UniversalExceptionHandler : ResponseEntityExceptionHandler(){

    @ExceptionHandler(value = [(UniversalAPIError::class)])
    fun handleBadRequest(e: UniversalAPIError, request: ServletWebRequest): ResponseEntity<Response>{
        val errorDetails = Response(e, request.request)

        return ResponseEntity(errorDetails, e.status)
    }

}