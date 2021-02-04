package com.syntax.asena.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.syntax.asena.error.UniversalAPIError
import com.syntax.asena.model.Response
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class UniversalAccessDeniedHandler : AccessDeniedHandler{

    override fun handle(
            request: HttpServletRequest,
            response: HttpServletResponse,
            accessDeniedException: AccessDeniedException
    ){
        @Suppress("ThrowableNotThrown")
        val error = Response(
                UniversalAPIError(HttpStatus.FORBIDDEN),
                request
        )

        response.apply{
            contentType = "application/json;charset=UTF-8"
            status = 403
            writer.write(ObjectMapper().writeValueAsString(error))
        }
    }

}