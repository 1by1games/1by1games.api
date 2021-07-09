package com.esgi.onebyone.application.rooms.get_room_by_id

import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.application.rooms.repositories.IRoomRepository
import com.esgi.onebyone.domain.room.RoomId
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import java.util.*
import org.springframework.stereotype.Component


data class GetRoomByIdQuery(val id: UUID) : Request<RoomResume>

@Component
class GetRoomByIdQueryHandler(val repository: IRoomRepository) : RequestHandler<GetRoomByIdQuery, RoomResume> {
    override fun handle(request: GetRoomByIdQuery): RoomResume {
        repository.findById(RoomId(request.id))?.let { room ->
            return RoomResume(
                id = room.id.value,
                name = room.name,
                author = room.author.id.value,
                roomSize = room.roomSize,
                currentDice = room.currentDice,
                members = room.members.map { member -> member.id.value },
            )
        }
        throw ApplicationException("Room not found")
    }
}