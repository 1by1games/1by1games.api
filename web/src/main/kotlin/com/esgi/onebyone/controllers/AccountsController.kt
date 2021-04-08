package com.esgi.onebyone.controllers

import com.esgi.onebyone.application.AccountsService
import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.application.entities.ConnectedUser
import com.esgi.onebyone.application.entities.UserEdition
import com.esgi.onebyone.application.get_all_accounts.GetAllAccountsQuery
import com.esgi.onebyone.application.login_user.UserLoginCommand
import com.esgi.onebyone.application.register_user.UserRegisterCommand
import com.esgi.onebyone.domain.account.Account
import com.esgi.onebyone.domain.account.AccountID
import io.jkratz.mediator.core.Command
import io.jkratz.mediator.core.Mediator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.util.*
import javax.annotation.security.RolesAllowed


@RestController
@RequestMapping("accounts")
open class AccountsController constructor(private val accountsService: AccountsService, private val mediator: Mediator) {


    @PostMapping("registration")
    open fun register(@RequestBody user: UserRegisterCommand): ResponseEntity<ConnectedUser> {
        return try{
            val created = mediator.dispatch(user)
            val uri = MvcUriComponentsBuilder.fromMethodName(AccountsController::class.java, "getById", created.id.toString()).build().toUri()
            return ResponseEntity.created(uri).build()
        }catch (e: ApplicationException){
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }


    @GetMapping("{id}")
    fun getById(@PathVariable id: UUID) : ResponseEntity<*> {
        return ResponseEntity.ok(null)
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
    fun getAll(): ResponseEntity<List<Account>> {
        return try {
            ResponseEntity.ok(mediator.dispatch(GetAllAccountsQuery()))
        } catch (e: ApplicationException) {
            ResponseEntity.notFound().build()
        }
    }

}