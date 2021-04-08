package com.esgi.onebyone.application.get_account_by_username

import com.esgi.onebyone.domain.account.Role
import java.util.*

data class UserResume(
        val id: UUID,
        val email: String,
        val role: Role, val
        username: String)