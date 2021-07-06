package com.esgi.onebyone.application.rooms.join_room

import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.application.accounts.repositories.IAccountsRepository
import com.esgi.onebyone.application.contracts.services.IHashingService
import com.esgi.onebyone.application.rooms.repositories.IRoomRepository
import com.esgi.onebyone.domain.account.AccountID
import com.esgi.onebyone.domain.room.Member
import com.esgi.onebyone.domain.room.RoomId
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import java.util.*

data class JoinRoomCommand(
    val roomId: UUID,
    val userId: UUID,
    val password: String,
) : Request<RoomId>

@Component
class JoinRoomCommandHandler(
    val roomRepository: IRoomRepository,
    val accountRepository: IAccountsRepository,
    val hashingService: IHashingService,
) : RequestHandler<JoinRoomCommand, RoomId> {
    override fun handle(request: JoinRoomCommand): RoomId {

        val user = accountRepository.findById(AccountID(request.userId))
            ?: throw ApplicationException("User not found")

        val room = roomRepository.findById(RoomId(request.roomId))


        room?.let { it ->

            room.isPasswordGood(hashingService.hashPassword(request.password))
            val member = Member(
                user.id,
                user.username,
                false,
                sortedSetOf(),
            )

            it.addMember(member)
            roomRepository.save(it)
            return it.id

        }

        throw ApplicationException("Room not found")

    }

}