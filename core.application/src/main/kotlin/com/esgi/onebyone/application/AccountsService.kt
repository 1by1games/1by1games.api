package com.esgi.onebyone.application

import com.esgi.onebyone.application.entities.ConnectedUser
import com.esgi.onebyone.application.entities.Credential
import com.esgi.onebyone.application.entities.UserEdition
import com.esgi.onebyone.application.entities.UserResume
import com.esgi.onebyone.application.repositories.IAccountsRepository
import com.esgi.onebyone.domain.account.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountsService @Autowired constructor(
        private val repository: IAccountsRepository,
        private val domainService: AccountsDomainService
)  {


    fun getCredentialByUsername(username: String): Credential {
        return Credential("toto", "todo")
    }

    fun getResumeByUsername(name: String): UserResume? {
        return UserResume(UUID.randomUUID(), "toto", Role.USER, "todo")
    }

    fun login(credentials: Any): ConnectedUser {
        return ConnectedUser(UUID.randomUUID(), "toto", Role.USER, "todo", "tada")
    }

    fun getByToken(token: String): ConnectedUser {
        return ConnectedUser(UUID.randomUUID(), "toto", Role.USER, "todo", "tada")
    }

    fun register(user: UserEdition): ConnectedUser {
        val existingAccounts = repository.findAll()

        if (domainService.isUnique(Email(user.email) , Username(user.username), existingAccounts)) {
            throw ApplicationException("non")
        }

        return ConnectedUser(UUID.randomUUID(), "toto", Role.USER, "todo", "tada")

    }
}