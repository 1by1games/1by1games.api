package com.esgi.onebyone.infrastructure.repositories

import com.esgi.onebyone.application.accounts.repositories.IAccountsRepository
import com.esgi.onebyone.domain.account.Account
import com.esgi.onebyone.domain.account.AccountID
import com.esgi.onebyone.infrastructure.mappers.to
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountRepositoryAdapter @Autowired constructor(private val repository: AccountRepository) : IAccountsRepository {

    override fun findAll(): List<Account> {
        return repository.findAll().map { it.to() }
    }

    override fun save(account: Account): AccountID {
        repository.saveAndFlush(account.to())
        return AccountID(UUID.randomUUID())
    }

    override fun getNewId(): AccountID {
        return AccountID(UUID.randomUUID())
    }

    override fun findByCredentials(username: String, password: String): Account? {
        return repository.findAccountByUsernameAndPassword(username, password)?.to()
    }

    override fun findByUsername(username: String): Account? {
        return repository.findAccountByUsername(username)?.to()
    }

    override fun findById(id: AccountID): Account? {
        return repository.findByIdOrNull(id.value)?.to()
    }


}