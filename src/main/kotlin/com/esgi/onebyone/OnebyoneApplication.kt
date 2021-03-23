package com.esgi.onebyone

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OnebyoneApplication

fun main(args: Array<String>) {
    runApplication<OnebyoneApplication>(*args)
    println("dogs")
}
