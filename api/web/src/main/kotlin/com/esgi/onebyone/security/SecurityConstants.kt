package com.esgi.onebyone.security

object SecurityConstants {
    const val LOGIN_URL = "/auth/login"
    const val SECRET = "SecretKeyToGenJWTs"
    const val EXPIRATION_TIME: Long = 864000000 // 10 days
    const val TOKEN_PREFIX = "Bearer "
    const val HEADER_STRING = "Authorization"
    const val SIGN_UP_URL = "/auth/register"
}