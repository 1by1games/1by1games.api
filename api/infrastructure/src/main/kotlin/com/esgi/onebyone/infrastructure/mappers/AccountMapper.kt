package com.esgi.onebyone.infrastructure.mappers

import com.esgi.onebyone.domain.account.*
import com.esgi.onebyone.infrastructure.models.AccountModel

fun AccountModel.to() = Account(AccountID(id), username, email, password, role)

fun Account.to() = AccountModel(id.value, username, email, password, role, listOf() )