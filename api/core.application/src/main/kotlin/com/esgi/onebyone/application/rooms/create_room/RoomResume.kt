package com.esgi.onebyone.application.rooms.create_room

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