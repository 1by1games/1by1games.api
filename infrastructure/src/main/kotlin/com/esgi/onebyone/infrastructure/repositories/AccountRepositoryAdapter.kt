package com.esgi.onebyone.infrastructure.repositories

import com.esgi.onebyone.application.repositories.IAccountsRepository
import com.esgi.onebyone.domain.account.Account
import com.esgi.onebyone.infrastructure.mappers.to
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountRepositoryAdapter @Autowired constructor(private val repository: AccountRepository) : IAccountsRepository {

    override fun findAll(): List<Account> {
        return repository.findAll().map { it.to() }
    }

    override fun save(account: Account) {
        repository.saveAndFlush(account.to())
    }
}