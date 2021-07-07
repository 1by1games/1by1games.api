package com.esgi.onebyone.infrastructure.webClient

import com.esgi.onebyone.application.webClient.WebClient
import org.springframework.stereotype.Component

@Component
class WebClientImpl: WebClient {
    override fun getRandomFromApi(value: Int, amount: Int): Int {
        return value
    }
}