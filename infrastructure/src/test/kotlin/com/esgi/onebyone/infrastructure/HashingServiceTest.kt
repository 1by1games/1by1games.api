package com.esgi.onebyone.infrastructure

import org.apache.commons.text.RandomStringGenerator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest


class HashingServiceTest {


    @Test
    fun hashing_good() {
        val generator = RandomStringGenerator.Builder()

        val key = generator.selectFrom(*"sachabailleul".toCharArray()).build().generate(8)

        Assertions.assertEquals("toto", key)
    }

}