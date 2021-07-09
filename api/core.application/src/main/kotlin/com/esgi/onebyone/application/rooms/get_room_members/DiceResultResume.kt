package com.esgi.onebyone.application.rooms.get_room_members

import com.esgi.onebyone.domain.room.Dice
import java.time.LocalDateTime

data class DiceResultResume(
    val dice : DiceResume,
    val result : Int,
    val throwDate : LocalDateTime
)
