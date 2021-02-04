package com.syntax.asena.controller

import com.syntax.asena.Build
import com.syntax.asena.error.UniversalAPIError
import com.syntax.asena.model.Developer
import com.syntax.asena.service.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Component
@RestController
@RequestMapping("/api/v${Build.VersionCodes.CUR_DEVELOPMENT}/developer")
class DeveloperController{

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @PostMapping("/${Build.Security.ENDPOINT}")
    fun authenticate(@RequestBody developer: Developer): String{
        try{
            authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(developer.username, developer.password)
            )
        }catch(e: Exception){
            throw UniversalAPIError(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
        }

        return tokenService.generateNewToken(developer.username)
    }

}