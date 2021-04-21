package com.esgi.onebyone.application.rooms.repositories

import com.esgi.onebyone.domain.room.Room
import com.esgi.onebyone.domain.room.RoomId

interface IRoomRepository {


    fun getNewId(): RoomId

    fun save(account: Room): RoomId

    fun doesRoomExistByNameAndState(name: String, state: Room.State) : Boolean
}