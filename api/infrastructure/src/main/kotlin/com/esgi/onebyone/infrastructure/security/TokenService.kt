package com.esgi.onebyone.infrastructure.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.esgi.onebyone.application.contracts.services.ITokenService
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService: ITokenService {
    override fun parse(token: String): String {
        return JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.toByteArray()))
                .build()
                .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .subject
    }

    override fun sign(claim: String): String {
        return JWT.create()
                .withSubject(claim)
                .withExpiresAt(Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.toByteArray()))
    }
}