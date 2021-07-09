package com.esgi.onebyone.infrastructure.webClient

import com.esgi.onebyone.application.webClient.ExternalRequestHandler
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient


@Component
class ExternalRequestHandlerImpl: ExternalRequestHandler {

    val randomApiUrl = "https://www.random.org/integers/?"

    override fun getRandomFromApi(value: Int, amount: Int): Int {
        val params = "num=1&min=$amount&max=${amount * value}&col=1&base=10&format=plain"
        val client = WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.ALL_VALUE)
            .baseUrl(randomApiUrl + params)
            .build()

        val a = client.get().retrieve().bodyToMono(String::class.java).block()!!

        return Integer.valueOf(a.trim(), 10)
    }
}