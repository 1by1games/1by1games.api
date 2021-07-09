package com.esgi.onebyone.application.rooms.repositories

import com.esgi.onebyone.domain.room.Member
import com.esgi.onebyone.domain.room.Room
import com.esgi.onebyone.domain.room.RoomId
import java.util.*

interface IRoomRepository {


    fun getNewId(): RoomId

    fun save(room: Room): RoomId

    fun doesRoomExistByNameAndState(name: String, state: Room.State) : Boolean
    fun findById(id: RoomId): Room?
    fun findMemberByAccountIdAndRoomId(accountId: UUID, roomId: UUID): Member?
    fun findMembersByRoomId(roomId: UUID): List<Member>
    fun findByName(roomName: String): Room?
}