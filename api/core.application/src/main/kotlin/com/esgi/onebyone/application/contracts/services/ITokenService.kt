package com.esgi.onebyone.application.contracts.services

interface ITokenService {

    fun parse(token: String): String
    fun sign(claim: String): String

}