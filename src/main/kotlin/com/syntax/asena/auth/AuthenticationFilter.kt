package com.syntax.asena.auth

import com.syntax.asena.Build.Security
import com.syntax.asena.service.TokenService
import com.syntax.asena.service.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationFilter : OncePerRequestFilter(){

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var userService: UserDetailsService

    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain){
        var token: String? = null
        var username: String? = null

        val header = req.getHeader(Security.HEADER)
        if(header !== null && header.startsWith(Security.TOKEN_PREFIX) && header.length > Security.TOKEN_PREFIX.length){
            token = header.substring(Security.TOKEN_PREFIX.length)
            username = tokenService.extractUsername(token)
        }

        if(username !== null && token !== null && SecurityContextHolder.getContext().authentication === null){
            val userDetails = userService.loadUserByUsername(username)
            if(tokenService.validateToken(token, userDetails)){
                SecurityContextHolder.getContext().apply{
                    authentication = UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.authorities
                    ).apply{
                        details = WebAuthenticationDetailsSource().buildDetails(req)
                    }
                }
            }
        }

        chain.doFilter(req, res)
    }

}