package com.esgi.onebyone.infrastructure.security

import com.esgi.onebyone.application.contracts.services.IHashingService
import org.springframework.stereotype.Service
import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

@Service
class HashingService : IHashingService {

    override fun hashPassword(password: String): String {
        return md5(password)
    }

    override fun matches(password: String, hashedPassword: String): Boolean {
        return hashedPassword == hashPassword(password)
    }

    private fun sha1(input: String) = hashString("SHA-1", input)
    private fun md5(input: String) = hashString("MD5", input)

    private fun hashString(type: String, input: String): String {
        val bytes = MessageDigest
                .getInstance(type)
                .digest(input.toByteArray())
        return DatatypeConverter.printHexBinary(bytes).toUpperCase()
    }
}