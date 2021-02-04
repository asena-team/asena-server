package com.syntax.asena.error

import org.springframework.http.HttpStatus

class UniversalAPIError(
        val status: HttpStatus,
        override val message: String? = null
): Exception(message)