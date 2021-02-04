package com.syntax.asena.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.syntax.asena.converter.LocalDateTimeFromStringConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import javax.annotation.PostConstruct

@Configuration
class MongoConfig : AbstractMongoClientConfiguration(){

    @Value("\${spring.data.mongodb.uri}")
    private final lateinit var uri: String

    @Value("\${spring.data.mongodb.database}")
    private final lateinit var database: String

    @Autowired
    private lateinit var mongoConverter: MappingMongoConverter

    @PostConstruct
    fun setUpMongoEscapeCharacterConversion(){
        mongoConverter.setMapKeyDotReplacement("_")
    }

    override fun getDatabaseName(): String = database

    override fun mongoClient(): MongoClient = MongoClients.create(uri)

    override fun mongoDbFactory(): MongoDatabaseFactory = SimpleMongoClientDatabaseFactory(mongoClient(), database)

    override fun mongoTemplate(databaseFactory: MongoDatabaseFactory, converter: MappingMongoConverter): MongoTemplate{
        return MongoTemplate(mongoDbFactory(), converter.apply{
            setTypeMapper(DefaultMongoTypeMapper(null))
        })
    }

    @Bean
    override fun customConversions(): MongoCustomConversions = MongoCustomConversions(
            listOf(LocalDateTimeFromStringConverter())
    )

}