package com.esgi.onebyone

import io.jkratz.mediator.spring.SpringMediator

import io.jkratz.mediator.core.Mediator
import io.jkratz.mediator.core.Registry

import io.jkratz.mediator.spring.SpringRegistry

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MediatorConfig (@Autowired val applicationContext : ApplicationContext) {

    @Bean
    fun registry(): Registry {
        return SpringRegistry(applicationContext)
    }

    @Bean
    fun mediator(registry: Registry): Mediator {
        return SpringMediator(registry)
    }

}