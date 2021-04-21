package com.esgi.onebyone.controllers

import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.application.accounts.login_user.ConnectedUser
import com.esgi.onebyone.application.accounts.register_user.UserRegisterCommand
import com.esgi.onebyone.application.rooms.create_room.CreateRoomCommand
import io.jkratz.mediator.core.Mediator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.noContent
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.util.*

@RestController
@RequestMapping("rooms")
class RoomsController constructor(private val mediator: Mediator) {

    @PostMapping
    open fun create(@RequestBody room: CreateRoomCommand): ResponseEntity<Unit> {
        return try{
            val created = mediator.dispatch(room)
            val uri = MvcUriComponentsBuilder.fromMethodName(RoomsController::class.java, "getById", created.value.toString()).build().toUri()
            return ResponseEntity.created(uri).build()
        }catch (e: ApplicationException){
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

    @GetMapping("{id}")
    fun getById(@PathVariable id: UUID)  {

    }
}