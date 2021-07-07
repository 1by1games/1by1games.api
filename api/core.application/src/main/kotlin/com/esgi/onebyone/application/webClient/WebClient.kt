package com.esgi.onebyone.application.webClient

interface WebClient {

    fun getRandomFromApi(value: Int, amount: Int): Int
}