package com.esgi.onebyone.application.rooms.get_room_by_id

import com.esgi.onebyone.domain.room.Dice
import java.util.*

data class RoomResume(
    val id: UUID,
    val name: String,
    val author: UUID,
    val roomSize: Int,
    val currentDice: Dice,
    val members: List<UUID>
) {
}