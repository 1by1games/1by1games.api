package com.esgi.onebyone.controllers

import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.application.accounts.queries.UserResume
import com.esgi.onebyone.application.accounts.login_user.ConnectedUser
import com.esgi.onebyone.application.accounts.queries.get_all_accounts.GetAllAccountsQuery
import com.esgi.onebyone.application.accounts.login_user.UserLoginCommand
import com.esgi.onebyone.application.accounts.queries.get_account_by_id.GetAccountByIdQuery
import com.esgi.onebyone.application.accounts.register_user.UserRegisterCommand
import io.jkratz.mediator.core.Mediator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.util.*


@RestController
@RequestMapping("accounts")
open class AccountsController constructor( private val mediator: Mediator) {


    @PostMapping("registration")
    open fun register(@RequestBody user: UserRegisterCommand): ResponseEntity<Unit> {
        return try{
            val created = mediator.dispatch(user)
            val uri = MvcUriComponentsBuilder.fromMethodName(AccountsController::class.java, "getById", created.value.toString()).build().toUri()
            return ResponseEntity.created(uri).build()
        }catch (e: ApplicationException){
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }


    @GetMapping("{id}")
    fun getById(@PathVariable id: UUID) : ResponseEntity<UserResume> {
        return try {
            ResponseEntity.ok(mediator.dispatch(GetAccountByIdQuery(id)))
        } catch (e: ApplicationException) {
            ResponseEntity.notFound().build()
        }
    }


    @PostMapping("authentication")
    fun authenticate(@RequestBody credentials: UserLoginCommand): ResponseEntity<ConnectedUser> {
        return try {
            ResponseEntity.ok(mediator.dispatch(credentials))
        } catch (e: ApplicationException) {
            ResponseEntity.notFound().build()
        }
    }


    @Secured("ROLE_ADMIN")
    @GetMapping
    fun getAll(): ResponseEntity<List<UserResume>> {
        return try {
            ResponseEntity.ok(mediator.dispatch(GetAllAccountsQuery()))
        } catch (e: ApplicationException) {
            ResponseEntity.notFound().build()
        }
    }

}