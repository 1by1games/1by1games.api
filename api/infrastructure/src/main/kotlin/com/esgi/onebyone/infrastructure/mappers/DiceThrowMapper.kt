package com.esgi.onebyone.infrastructure.mappers

import com.esgi.onebyone.domain.room.Dice
import com.esgi.onebyone.domain.room.DiceResult
import com.esgi.onebyone.infrastructure.models.DiceThrowModel
import com.esgi.onebyone.infrastructure.models.MemberModel
import java.util.*


fun DiceThrowModel.to() = DiceResult(Dice(size, amount), result, throwDate)

fun DiceResult.to(member : MemberModel) = DiceThrowModel( UUID.randomUUID(), dice.size, dice.amount, result, throwDate, member = member )