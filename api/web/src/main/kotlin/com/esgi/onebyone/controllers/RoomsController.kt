package com.esgi.onebyone.controllers

import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.application.accounts.queries.get_account_by_username.GetAccountByUsernameQuery
import com.esgi.onebyone.application.rooms.create_room.CreateRoomCommand
import com.esgi.onebyone.application.rooms.get_room_by_id.GetRoomByIdQuery
import com.esgi.onebyone.application.rooms.get_room_by_id.RoomResume
import com.esgi.onebyone.application.rooms.get_room_members.GetMembersByRoomIdQuery
import com.esgi.onebyone.application.rooms.get_room_members.MemberResume
import com.esgi.onebyone.application.rooms.join_room.JoinRoomCommand
import com.esgi.onebyone.application.rooms.join_room.LeaveRoomCommand
import com.esgi.onebyone.application.rooms.throw_dice_command.ThrowDiceCommand
import com.esgi.onebyone.application.security.parse_token.ParseTokenQuery
import com.esgi.onebyone.domain.commons.exceptions.DomainException
import com.esgi.onebyone.dto.RoomSubscriptionDTO
import com.esgi.onebyone.dto.ThrowDiceDTO
import io.jkratz.mediator.core.Mediator
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.util.*
import javax.transaction.Transactional


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

    @GetMapping("{roomId}/members")
    fun getMembersByRoomID(
        @PathVariable roomId: UUID,
        @RequestHeader headers: HttpHeaders
    ): ResponseEntity<List<MemberResume>> {
        return try {
            val token = headers.getFirst(HttpHeaders.AUTHORIZATION) ?: throw Exception("no token")
            val username = mediator.dispatch(ParseTokenQuery(token))
            val account = mediator.dispatch(GetAccountByUsernameQuery(username))
            val members = mediator.dispatch(GetMembersByRoomIdQuery(roomId, account.id))

            ResponseEntity.ok(members)
        } catch (e: ApplicationException) {
            ResponseEntity.notFound().build()
        } catch (e: DomainException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{roomId}/subscription")
    @Transactional
    fun leave(
        @PathVariable roomId: UUID,
        @RequestHeader headers: HttpHeaders
    ): ResponseEntity<Unit> {
        return try {
            val token = headers.getFirst(HttpHeaders.AUTHORIZATION) ?: throw Exception("no token")
            val username = mediator.dispatch(ParseTokenQuery(token))
            val account = mediator.dispatch(GetAccountByUsernameQuery(username))
            val created = mediator.dispatch(LeaveRoomCommand(roomId, account.id))

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

    @PutMapping("/{roomName}/subscription")
    fun join(
        @PathVariable roomName: String,
        @RequestBody roomSubscriptionDTO: RoomSubscriptionDTO,
        @RequestHeader headers: HttpHeaders
    ): ResponseEntity<Unit> {
        return try {
            val token = headers.getFirst(HttpHeaders.AUTHORIZATION) ?: throw Exception("no token")
            val username = mediator.dispatch(ParseTokenQuery(token))
            val account = mediator.dispatch(GetAccountByUsernameQuery(username))
            val created = mediator.dispatch(JoinRoomCommand(roomName, account.id, roomSubscriptionDTO.password))

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

    @PostMapping("/{roomId}/dicesThrow")
    fun throwDices(
        @RequestBody(required = false) throwDice: ThrowDiceDTO?,
        @RequestHeader headers: HttpHeaders,
        @PathVariable roomId: UUID,
    ): ResponseEntity<Unit> {
        return try {
            val token = headers.getFirst(HttpHeaders.AUTHORIZATION) ?: throw Exception("no token")
            val username = mediator.dispatch(ParseTokenQuery(token))
            val account = mediator.dispatch(GetAccountByUsernameQuery(username))
            mediator.dispatch(ThrowDiceCommand(throwDice?.amount, throwDice?.value, roomId, account.id))

            return ResponseEntity.ok().build()

        } catch (e: ApplicationException) {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        } catch (e: DomainException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }
}
