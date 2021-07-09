package com.esgi.onebyone.application.rooms.get_room_members

import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.application.rooms.repositories.IRoomRepository
import com.esgi.onebyone.domain.room.RoomId
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import java.util.*
import org.springframework.stereotype.Service
import org.springframework.stereotype.Component

data class GetMembersByRoomIdQuery(val roomId: UUID, val userId: UUID) : Request<List<MemberResume>>


@Component
class GetMembersByRoomIdQueryHandler(val roomRepository: IRoomRepository): RequestHandler<GetMembersByRoomIdQuery, List<MemberResume>> {
    override fun handle(request: GetMembersByRoomIdQuery): List<MemberResume> {
        val room = roomRepository.findById(RoomId(request.roomId))
            ?: throw ApplicationException("No room found")


        room.canUserGetInfo(request.userId)

        val roomMembers = roomRepository.findMembersByRoomId(request.roomId)


        return roomMembers.map { member ->
            MemberResume(
                username = member.username,
                isAuthor = member.isAuthor,
                diceThrows = member.diceThrows.map { diceResult ->
                    DiceResultResume(
                        dice = DiceResume(
                            size = diceResult.dice.size,
                            amount = diceResult.dice.amount,
                        ),
                        result = diceResult.result,
                        throwDate = diceResult.throwDate
                    )
                },
                userId = member.accountId,
            )
        }
    }
}