package com.esgi.onebyone.domain.account

class Account(
        val id: AccountID,
        val username: String,
        val email: String,
        val password: String,
        var role: Role = Role.USER
        )
{

    private fun isSimilarByNameOrEmail(account: Account) : Boolean {
        return account.email == email || account.username == username
    }

    fun isUnique( accounts : List<Account>) : Boolean {
        return accounts.none { this.isSimilarByNameOrEmail(it) }
    }

    fun setRole( accounts : List<Account> ) {
        if ( accounts.none {it.role == Role.ADMIN}) {
            this.role = Role.ADMIN
        }
    }
}