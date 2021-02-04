package com.syntax.asena.config

import com.syntax.asena.Build
import com.syntax.asena.Build.Security
import com.syntax.asena.auth.AuthenticationEntryPoint
import com.syntax.asena.auth.AuthenticationFilter
import com.syntax.asena.auth.UniversalAccessDeniedHandler
import com.syntax.asena.service.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter(){

    @Autowired
    private lateinit var authenticationFilter: AuthenticationFilter

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Value("\${springdoc.api-docs.path}")
    private lateinit var apiDocsPath: String

    @Value("\${springdoc.swagger-ui.path}")
    private lateinit var swaggerUIPath: String

    override fun configure(http: HttpSecurity){
        http
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers(
                "/api/v${Build.VersionCodes.CUR_DEVELOPMENT}/developer/${Security.ENDPOINT}",
                "/swagger-ui/**", // Redirected Swagger UI
                swaggerUIPath, // Swagger UI
                "${apiDocsPath}/**" // Open API Docs
            )
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler()).authenticationEntryPoint(authenticationEntryPoint())
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder){
        auth.userDetailsService(userDetailsService)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = NoOpPasswordEncoder.getInstance()

    @Bean
    fun authenticationEntryPoint(): AuthenticationEntryPoint = AuthenticationEntryPoint()

    @Bean
    fun accessDeniedHandler(): UniversalAccessDeniedHandler = UniversalAccessDeniedHandler()

    @Bean(name = [BeanIds.AUTHENTICATION_MANAGER])
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

}