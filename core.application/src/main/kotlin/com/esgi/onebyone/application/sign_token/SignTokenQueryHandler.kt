package com.esgi.onebyone.application.sign_token

import com.esgi.onebyone.application.contracts.services.ITokenService
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

data class SignTokenQuery(val input : String ) : Request<String>

@Component
class SignTokenQueryHandler @Autowired constructor(private val tokenService: ITokenService) : RequestHandler<SignTokenQuery, String> {
    override fun handle(request: SignTokenQuery): String {
        return tokenService.sign(request.input)
    }
}