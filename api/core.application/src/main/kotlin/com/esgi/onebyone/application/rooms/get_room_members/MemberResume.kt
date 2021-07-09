package com.esgi.onebyone.application.rooms.get_room_members

import java.util.*

data class MemberResume(
                        val username: String,
                        var isAuthor: Boolean,
                        val diceThrows: List<DiceResultResume>,
                        val userId: UUID?) {

}