package com.esgi.onebyone.application.get_all_accounts

import com.esgi.onebyone.application.repositories.IAccountsRepository
import com.esgi.onebyone.domain.account.Account
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

class GetAllAccountsQuery: Request<List<Account>>

@Component
class GetAllAccountsQueryHandler @Autowired constructor(private val repository: IAccountsRepository) : RequestHandler<GetAllAccountsQuery, List<Account>> {
    override fun handle(request: GetAllAccountsQuery): List<Account> {
        return repository.findAll()
    }
}