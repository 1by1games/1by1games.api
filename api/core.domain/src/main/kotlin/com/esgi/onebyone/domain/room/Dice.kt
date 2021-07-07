package com.esgi.onebyone.domain.room

import com.esgi.onebyone.domain.commons.exceptions.DomainException

class Dice(
    val size: Int,
    val amount: Int
) {


    fun canBeThrow() {
        if (size < 2 || size > 100 ) {
            throw DomainException("Die value shall be between 2 and 100")
        }

        if (amount < 1 || amount > 20) {
            throw DomainException("You can't throw less than 1 die and more than 20 dice")
        }
    }
}