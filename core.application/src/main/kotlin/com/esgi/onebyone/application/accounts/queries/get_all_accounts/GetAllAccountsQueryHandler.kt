package com.esgi.onebyone.application.accounts.queries.get_all_accounts

import com.esgi.onebyone.application.accounts.queries.UserResume
import com.esgi.onebyone.application.accounts.repositories.IAccountsRepository
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