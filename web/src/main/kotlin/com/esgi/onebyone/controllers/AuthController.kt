package com.esgi.onebyone.controllers

import com.esgi.onebyone.application.AccountsService
import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.application.entities.ConnectedUser
import com.esgi.onebyone.application.entities.UserEdition
import com.esgi.onebyone.application.login_user.UserLoginCommand
import io.jkratz.mediator.core.Command
import io.jkratz.mediator.core.Mediator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

data class TestClass(val con : String): Command


@RestController
@RequestMapping("auth")
open class AuthController constructor(private val accountsService: AccountsService, private val mediator: Mediator) {


    @PostMapping("register")
    open fun register(@RequestBody user: UserEdition): ResponseEntity<ConnectedUser> {
        return try{
            ResponseEntity.ok(accountsService.register(user))
        }catch (e: ApplicationException){
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }


    @PostMapping("login")
    fun login(@RequestBody credentials: UserLoginCommand): ResponseEntity<ConnectedUser> {
        return try {
            ResponseEntity.ok(mediator.dispatch(credentials))
        } catch (e: ApplicationException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    open fun getByToken(@RequestParam token: String): ResponseEntity<ConnectedUser> {
        return try {
            ResponseEntity.ok(accountsService.getByToken(token))
        } catch (e: ApplicationException) {
            ResponseEntity.notFound().build()
        }
    }
}