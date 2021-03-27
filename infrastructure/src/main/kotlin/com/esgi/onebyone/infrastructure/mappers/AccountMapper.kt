package com.esgi.onebyone.infrastructure.mappers

import com.esgi.onebyone.domain.account.*
import com.esgi.onebyone.infrastructure.Account as InfraAccount

fun InfraAccount.to() = Account(AccountID(id), Username(username), Email(email), Password(password))

fun Account.to() = InfraAccount(id.id, username.value, email.value, password.value)