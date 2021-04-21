package com.esgi.onebyone

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
open class WebConfig {
    @Bean
    open fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }


    @Bean
    open fun corsConfigurer(): WebMvcConfigurer? {
        return object : WebMvcConfigurerAdapter() {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/*")
                        .exposedHeaders(HttpHeaders.LOCATION)
                        .allowedHeaders(HttpHeaders.LOCATION)
                        .allowedMethods(*HttpMethod.values().map { it.toString() }.toTypedArray())
            }

            override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
                configurer.defaultContentType(MediaType.APPLICATION_JSON)
                        .mediaType("json", MediaType.APPLICATION_JSON)
            }
        }
    }
}