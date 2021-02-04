package com.syntax.asena.config

import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class SSLConfig{

    @PostConstruct
    fun configureSSL(){
        System.setProperty("https.protocols", "TLSv1.2")
    }

}