package com.esgi.onebyone.infrastructure.mappers

import com.esgi.onebyone.domain.room.Dice
import com.esgi.onebyone.domain.room.Room
import com.esgi.onebyone.domain.room.RoomId
import com.esgi.onebyone.infrastructure.models.RoomModel


fun RoomModel.to() = Room(
        id = RoomId(id),
        name = name,
        password = password,
        _members = members?.map { it.to() }?.toMutableList() ?: mutableListOf(),
        state = state,
        creationDate = creationDate,
        endDate = endDate,
        roomSize = roomSize,
        currentDice = Dice(currentDiceSize, currentDiceAmount))

fun Room.to() = RoomModel(
        id.value,
        name,
        password,
        state,
        roomSize,
        creationDate,
        endDate,
        currentDice.size,
        currentDice.amount,
        null)