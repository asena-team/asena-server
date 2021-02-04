package com.syntax.asena.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.syntax.asena.error.UniversalAPIError
import com.syntax.asena.model.Response
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationEntryPoint : AuthenticationEntryPoint{

    override fun commence(
            request: HttpServletRequest,
            response: HttpServletResponse,
            authException: AuthenticationException
    ){
        @Suppress("ThrowableNotThrown")
        val error = Response(
                UniversalAPIError(HttpStatus.UNAUTHORIZED),
                request
        )

        response.apply{
            contentType = "application/json;charset=UTF-8"
            status = 403
            writer.write(ObjectMapper().writeValueAsString(error))
        }
    }

}