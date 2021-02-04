package com.syntax.asena.service

import com.syntax.asena.error.UniversalAPIError
import com.syntax.asena.repository.DeveloperRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsService : UserDetailsService{

    @Autowired
    private lateinit var repository: DeveloperRepository

    override fun loadUserByUsername(username: String): UserDetails{
        val developer = repository.findByUsername(username) ?: throw UniversalAPIError(HttpStatus.UNAUTHORIZED)

        return User(developer.username, developer.password, emptyList())
    }

}