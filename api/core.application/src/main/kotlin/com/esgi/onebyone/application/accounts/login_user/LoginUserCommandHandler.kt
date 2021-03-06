package com.esgi.onebyone.application.accounts.login_user

import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.application.contracts.services.IHashingService
import com.esgi.onebyone.application.contracts.services.ITokenService
import com.esgi.onebyone.application.accounts.repositories.IAccountsRepository
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

data class UserLoginCommand(var username: String, var password: String) : Request<ConnectedUser>

@Component
class LoginUserCommandHandler @Autowired constructor(
        private val repository: IAccountsRepository,
        private val tokenService: ITokenService,
        private val securityService: IHashingService
) : RequestHandler<UserLoginCommand, ConnectedUser> {

    override fun handle(request: UserLoginCommand): ConnectedUser {
        val user = repository.findByCredentials(request.username, securityService.hashPassword(request.password))

        user?.let {
            return ConnectedUser(
                    id = user.id.value,
                    email = user.email,
                    role = user.role,
                    username = user.username,
                    token = tokenService.sign(user.username)
            )
        }
        throw ApplicationException("user not found")

    }

}

