package com.esgi.onebyone.infrastructure.repositories

import com.esgi.onebyone.infrastructure.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : JpaRepository<Account, UUID> {

    fun findAccountByUsernameAndPassword(username: String, password: String) : Account?

    fun findAccountByUsername(username: String): Account?
}