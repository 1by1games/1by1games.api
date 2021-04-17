package com.esgi.onebyone.application.accounts.queries.get_account_by_id

import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.application.accounts.queries.UserResume
import com.esgi.onebyone.application.accounts.queries.get_account_by_username.GetAccountByUsernameQuery
import com.esgi.onebyone.application.accounts.repositories.IAccountsRepository
import com.esgi.onebyone.domain.account.AccountID
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*


data class GetAccountByIdQuery (val id : UUID): Request<UserResume>


@Component
class GetAccountByIdQueryHandler constructor(private val repository: IAccountsRepository) : RequestHandler<GetAccountByIdQuery, UserResume> {
    override fun handle(request: GetAccountByIdQuery): UserResume {
        repository.findById(AccountID(request.id))?.let {
            return UserResume(
                    it.id.id,
                    it.email,
                    it.role,
                    it.username)
        }

        throw ApplicationException("User not found")

    }
}