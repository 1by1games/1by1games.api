package com.esgi.onebyone.application.rooms.join_room

import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.application.accounts.repositories.IAccountsRepository
import com.esgi.onebyone.application.rooms.repositories.IRoomRepository
import com.esgi.onebyone.domain.account.AccountID
import com.esgi.onebyone.domain.room.Member
import com.esgi.onebyone.domain.room.RoomId
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import java.util.*

data class LeaveRoomCommand(
    val roomId: UUID,
    val userId: UUID,
) : Request<RoomId>

@Component
class LeaveRoomCommandHandler(
    val roomRepository: IRoomRepository,
    val accountRepository: IAccountsRepository,
) : RequestHandler<LeaveRoomCommand, RoomId> {
    override fun handle(request: LeaveRoomCommand): RoomId {
        val user = accountRepository.findById(AccountID(request.userId))
            ?: throw ApplicationException("User not found")

        val room = roomRepository.findById(RoomId(request.roomId))

        room?.let { it ->
            val member = Member(
                user.id,
                user.username,
                false,
                sortedSetOf(),
                request.userId,
            )

            it.removeMember(member)
            roomRepository.save(it)
            return it.id
        }

        throw ApplicationException("Room not found")
    }

}