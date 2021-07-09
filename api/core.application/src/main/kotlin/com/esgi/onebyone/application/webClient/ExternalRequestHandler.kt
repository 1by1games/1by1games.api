package com.esgi.onebyone.application.webClient

interface ExternalRequestHandler {

    fun getRandomFromApi(value: Int, amount: Int): Int
}