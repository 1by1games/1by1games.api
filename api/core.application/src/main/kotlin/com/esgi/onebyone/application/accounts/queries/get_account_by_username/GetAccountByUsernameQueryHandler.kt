package com.esgi.onebyone.application.accounts.queries.get_account_by_username

import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.application.accounts.queries.UserResume
import com.esgi.onebyone.application.accounts.repositories.IAccountsRepository
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


data class GetAccountByUsernameQuery(val username : String): Request<UserResume>

@Component
class GetAccountByUsernameQueryHandler @Autowired constructor(private val repository: IAccountsRepository) : RequestHandler<GetAccountByUsernameQuery, UserResume> {
    override fun handle(request: GetAccountByUsernameQuery): UserResume {
        repository.findByUsername(request.username)?.let {
            return UserResume(
                it.id.value,
                it.email,
                it.role,
                it.username)
        }

        throw ApplicationException("User not found")
    }
}