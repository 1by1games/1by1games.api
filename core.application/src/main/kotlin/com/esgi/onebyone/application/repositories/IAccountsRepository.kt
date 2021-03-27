package com.esgi.onebyone.application.repositories

import com.esgi.onebyone.domain.account.Account
import com.esgi.onebyone.domain.account.AccountID

interface IAccountsRepository {

    fun findAll(): List<Account>

    fun save(account: Account): AccountID

}