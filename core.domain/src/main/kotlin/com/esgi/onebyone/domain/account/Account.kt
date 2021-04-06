package com.esgi.onebyone.domain.account

class Account(
        val id: AccountID,
        val username: Username,
        val email: Email,
        val password: Password)
{

    fun isSimilarByNameOrEmail(otherEmail: Email, otherUsername: Username) : Boolean {
        return otherEmail == email || otherUsername == username
    }
}