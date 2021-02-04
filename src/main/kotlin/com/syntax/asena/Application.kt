package com.syntax.asena

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(info = Info(
	title = "Asena API",
	description = "Asena API Server Docs",
	version = "v${Build.VersionCodes.CUR_DEVELOPMENT}"
))
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
