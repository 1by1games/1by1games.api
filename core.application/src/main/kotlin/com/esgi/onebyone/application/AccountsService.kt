package com.esgi.onebyone.application

import com.esgi.onebyone.domain.Credential
import com.esgi.onebyone.domain.Role
import com.esgi.onebyone.domain.UserResume
import java.util.*

class AccountsService {

    fun getCredentialByUsername(username: String): Credential {
        return Credential("toto", "todo")
    }

    fun getResumeByUsername(name: String): UserResume? {
        return UserResume(UUID.randomUUID(), "toto", Role.USER, "todo")
    }
}