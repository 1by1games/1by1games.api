package com.esgi.onebyone.infrastructure.repositories

import com.esgi.onebyone.infrastructure.models.AccountModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : JpaRepository<AccountModel, UUID> {

    fun findAccountByUsernameAndPassword(username: String, password: String) : AccountModel?

    fun findAccountByUsername(username: String): AccountModel?
}