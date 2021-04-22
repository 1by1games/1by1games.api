package com.esgi.onebyone.domain.room

import java.time.LocalDateTime

class DiceResult (
        val dice : Dice,
        val result : Int,
        val throwDate : LocalDateTime) {
}