package com.esgi.onebyone.domain.account

import org.springframework.stereotype.Service

@Service
class AccountsDomainService {
    fun isUnique( email: Email, username: Username, accounts : List<Account>): Boolean {
        return accounts.none { it.isSimilarByNameOrEmail(email, username) }
    }
}