package com.syntax.asena.error

import com.syntax.asena.model.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class ExceptionResolver{

    @ExceptionHandler(value = [(NoHandlerFoundException::class)])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundError(e: NoHandlerFoundException, request: ServletWebRequest): ResponseEntity<Response> {
        val errorDetails = Response(
                UniversalAPIError(HttpStatus.NOT_FOUND),
                request.request
        )

        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

}