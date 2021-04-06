package com.esgi.onebyone.application.login_user

import com.esgi.onebyone.application.entities.ConnectedUser
import com.esgi.onebyone.domain.account.Role
import io.jkratz.mediator.core.Command
import io.jkratz.mediator.core.CommandHandler
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import java.util.*

data class UserLoginCommand(var username: String, var password: String) : Request<ConnectedUser>

@Component
class LoginUserCommandHandler : RequestHandler<UserLoginCommand, ConnectedUser> {
    override fun handle(request: UserLoginCommand): ConnectedUser {
        return ConnectedUser(UUID.randomUUID(), "toto", Role.USER, "todo", "tada")
    }

}

