package com.esgi.onebyone.application.entities

import com.esgi.onebyone.domain.account.Role
import java.util.*

data class ConnectedUser(
        val id: UUID,
        val email: String,
        val role: Role,
        val username: String,
        val token: String)