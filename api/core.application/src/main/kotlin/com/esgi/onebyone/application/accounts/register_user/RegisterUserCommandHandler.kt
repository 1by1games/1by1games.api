package com.esgi.onebyone.application.accounts.register_user

import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.application.contracts.services.IHashingService
import com.esgi.onebyone.application.accounts.repositories.IAccountsRepository
import com.esgi.onebyone.domain.account.*
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

data class UserRegisterCommand(var username: String, var password: String, var email: String) : Request<AccountID>

@Component
class RegisterUserCommandHandler @Autowired constructor(private val repository: IAccountsRepository, private val hashingService: IHashingService) : RequestHandler<UserRegisterCommand, AccountID> {
        override fun handle(request: UserRegisterCommand): AccountID {

            val existingAccounts = repository.findAll()

            val newAccount = Account(
                    repository.getNewId(),
                    request.username,
                    request.email,
                    hashingService.hashPassword(request.password)
            )
            newAccount.setRole(existingAccounts)

            if ( !newAccount.isUnique(existingAccounts)) {
                throw ApplicationException("conflict")
            }

            return repository.save(newAccount)
    }
}