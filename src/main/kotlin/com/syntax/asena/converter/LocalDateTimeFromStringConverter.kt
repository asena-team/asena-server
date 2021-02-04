package com.syntax.asena.converter

import org.springframework.core.convert.converter.Converter
import java.time.LocalDateTime

class LocalDateTimeFromStringConverter : Converter<String, LocalDateTime>{

    override fun convert(source: String): LocalDateTime = LocalDateTime.parse(source)

}