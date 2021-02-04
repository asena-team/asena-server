package com.syntax.asena.config

import com.syntax.asena.context.Queues
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQListenerConfig{

    @Bean
    fun queue(): Queue = Queue(Queues.PREMIUM.bindingName, false)

    @Bean
    fun exchange(): TopicExchange = TopicExchange("spring-boot-exchange")

    @Bean
    fun jsonMessageConverter(): MessageConverter = Jackson2JsonMessageConverter()

}