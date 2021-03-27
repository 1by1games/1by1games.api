package com.esgi.onebyone.controllers

import com.esgi.onebyone.application.AccountsService
import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.domain.ConnectedUser
import com.esgi.onebyone.domain.UserEdition
import com.esgi.onebyone.domain.UserLogin
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("auth")
open class AuthController constructor(private val accountsService: AccountsService) {


    @PostMapping("register")
    open fun register(@RequestBody user: UserEdition): ResponseEntity<ConnectedUser> {
        return try{
            ResponseEntity.ok(accountsService.register(user))
        }catch (e: ApplicationException){
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }


    @PostMapping("login")
    fun login(@RequestBody credentials: UserLogin): ResponseEntity<ConnectedUser> {
        return try {
            ResponseEntity.ok(accountsService.login(credentials))
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