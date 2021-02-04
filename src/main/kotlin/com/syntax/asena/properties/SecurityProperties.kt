package com.syntax.asena.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "com.syntax.asena.secret")
data class SecurityProperties(
    var key: String = ""
)