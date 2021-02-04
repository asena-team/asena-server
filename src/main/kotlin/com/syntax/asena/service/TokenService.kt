package com.syntax.asena.service

import com.syntax.asena.properties.SecurityProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function

@Service
class TokenService{

    @Autowired
    private lateinit var security: SecurityProperties

    fun extractUsername(token: String): String{
        return extractClaim(token, Function { obj: Claims ->
            obj.subject
        })
    }

    fun extractExpiration(token: String): Date{
        return extractClaim(token, Function { obj: Claims ->
            obj.expiration
        })
    }

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T{
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String): Claims{
        return Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(security.key.toByteArray()))
                .build()
                .parseClaimsJws(token)
                .body
    }

    private fun isTokenExpired(token: String): Boolean{
        return extractExpiration(token).before(Date())
    }

    fun generateNewToken(username: String): String{
        return buildToken(mutableMapOf(), username)
    }

    fun buildToken(claims: MutableMap<String, Any>, subject: String): String{
        return Jwts
                .builder()
                .addClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(Int.MAX_VALUE * 1000L))
                .signWith(
                        Keys.hmacShaKeyFor(security.key.toByteArray()),
                        SignatureAlgorithm.HS256
                )
                .compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean{
        extractUsername(token).apply{
            return this == userDetails.username && !isTokenExpired(token)
        }
    }

}