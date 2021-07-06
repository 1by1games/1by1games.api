package com.esgi.onebyone.infrastructure.controllers

import com.esgi.onebyone.application.accounts.queries.get_account_by_username.GetAccountByUsernameQuery
import com.esgi.onebyone.application.security.parse_token.ParseTokenQuery
import com.esgi.onebyone.infrastructure.sse.SseHandler
import io.jkratz.mediator.core.Mediator
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter


@Controller
@RequestMapping("sse")
class SseController(
    private val sseHandler: SseHandler,
    private val mediator: Mediator
) {
    @GetMapping()
    fun subscribe(@RequestHeader headers: HttpHeaders): SseEmitter? {
        val token = headers.getFirst(HttpHeaders.AUTHORIZATION) ?: throw Exception("no token")
        val username = mediator.dispatch(ParseTokenQuery(token))
        val account = mediator.dispatch(GetAccountByUsernameQuery(username))
        return sseHandler.subscribeToSse(account.id.toString())
    }
}