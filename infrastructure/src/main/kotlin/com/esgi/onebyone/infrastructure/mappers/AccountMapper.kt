package com.esgi.onebyone.infrastructure.mappers

import com.esgi.onebyone.domain.account.*
import com.esgi.onebyone.infrastructure.models.AccountModel as InfraAccount

fun InfraAccount.to() = Account(AccountID(id), username, email, password, role)

fun Account.to() = InfraAccount(id.id, username, email, password, role)