package com.esgi.onebyone.controllers

import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.application.accounts.queries.get_account_by_username.GetAccountByUsernameQuery
import com.esgi.onebyone.application.rooms.create_room.CreateRoomCommand
import com.esgi.onebyone.application.rooms.create_room.RoomResume
import com.esgi.onebyone.application.rooms.get_room_by_id.GetRoomByIdQuery
import com.esgi.onebyone.application.rooms.join_room.JoinRoomCommand
import com.esgi.onebyone.application.security.parse_token.ParseTokenQuery
import com.esgi.onebyone.domain.commons.exceptions.DomainException
import io.jkratz.mediator.core.Mediator
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.util.*


@RestController
@RequestMapping("rooms")
class RoomsController constructor(private val mediator: Mediator) {

    @PostMapping
    fun create(@RequestBody room: CreateRoomCommand): ResponseEntity<Unit> {
        return try {
            val created = mediator.dispatch(room)
            val uri =
                MvcUriComponentsBuilder.fromMethodName(RoomsController::class.java, "getById", created.value.toString())
                    .build().toUri()
            return ResponseEntity.created(uri).build()
        } catch (e: ApplicationException) {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

    @GetMapping("{id}")
    fun getById(@PathVariable id: UUID): ResponseEntity<RoomResume> {
        return try {
            ResponseEntity.ok(mediator.dispatch(GetRoomByIdQuery(id)))
        } catch (e: ApplicationException) {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{roomId}/join")
    fun join(
        @PathVariable roomId: UUID,
        @RequestHeader headers: HttpHeaders
    ): ResponseEntity<Unit> {
        return try {
            val token = headers.getFirst(HttpHeaders.AUTHORIZATION) ?: throw Exception("no token")
            val username = mediator.dispatch(ParseTokenQuery(token))
            val account = mediator.dispatch(GetAccountByUsernameQuery(username))
            val created = mediator.dispatch(JoinRoomCommand(roomId, account.id))

            val uri =
                MvcUriComponentsBuilder.fromMethodName(RoomsController::class.java, "getById", created.value.toString())
                    .build().toUri()
            return ResponseEntity.created(uri).build()

        } catch (e: ApplicationException) {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        } catch (e: DomainException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }
}


