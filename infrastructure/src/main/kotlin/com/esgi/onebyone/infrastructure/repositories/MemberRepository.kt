package com.esgi.onebyone.infrastructure.repositories

import com.esgi.onebyone.infrastructure.models.MemberModel
import com.esgi.onebyone.infrastructure.models.RoomModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface MemberRepository : JpaRepository<MemberModel, UUID> {

    fun findByAccount_IdAndRoom_Id(accountId: UUID, roomId: UUID): List<MemberModel>
}