package com.esgi.onebyone.infrastructure.repositories

import com.esgi.onebyone.domain.room.Room
import com.esgi.onebyone.infrastructure.models.AccountModel
import com.esgi.onebyone.infrastructure.models.RoomModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface RoomRepository : JpaRepository<RoomModel, UUID> {

    fun getByNameAndState(name: String, state: Room.State) : List<RoomModel>
    fun getByName(name: String): RoomModel?
}