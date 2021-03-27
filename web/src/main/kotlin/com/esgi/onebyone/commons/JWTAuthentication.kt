package com.esgi.onebyone.commons

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.esgi.onebyone.security.SecurityConstants
import java.util.*

object JWTAuthentication {
    fun parse(token: String): String {
        return JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.toByteArray()))
                .build()
                .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .subject
    }

    fun sign(claim: String): String {
        return JWT.create()
                .withSubject(claim)
                .withExpiresAt(Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.toByteArray()))
    }

}