package com.esgi.onebyone.application.accounts.repositories

import com.esgi.onebyone.domain.account.Account
import com.esgi.onebyone.domain.account.AccountID
import java.util.*

interface IAccountsRepository {

    fun findAll(): List<Account>

    fun save(account: Account): AccountID
    fun getNewId(): AccountID
    fun findByCredentials(username: String, password: String): Account?

    fun findByUsername(username: String): Account?

    fun findById(id : AccountID): Account?

}