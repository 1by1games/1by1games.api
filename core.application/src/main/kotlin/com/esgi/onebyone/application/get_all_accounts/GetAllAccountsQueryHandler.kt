package com.esgi.onebyone.application.get_all_accounts

import com.esgi.onebyone.application.get_account_by_username.UserResume
import com.esgi.onebyone.application.repositories.IAccountsRepository
import com.esgi.onebyone.domain.account.Account
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

class GetAllAccountsQuery: Request<List<UserResume>>

@Component
class GetAllAccountsQueryHandler @Autowired constructor(private val repository: IAccountsRepository) : RequestHandler<GetAllAccountsQuery, List<UserResume>> {
    override fun handle(request: GetAllAccountsQuery): List<UserResume> {
        return repository.findAll().map {
            UserResume(it.id.id, it.email, it.role, it.username)
        }
    }
}