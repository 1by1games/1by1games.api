package com.esgi.onebyone.infrastructure.repositories

import com.esgi.onebyone.application.rooms.repositories.IRoomRepository
import com.esgi.onebyone.domain.room.DiceResult
import com.esgi.onebyone.domain.room.Room
import com.esgi.onebyone.domain.room.RoomId
import com.esgi.onebyone.infrastructure.mappers.to
import com.esgi.onebyone.infrastructure.models.DiceThrowModel
import com.esgi.onebyone.infrastructure.models.MemberModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoomRepositoryAdapter @Autowired constructor(
        private val roomRepository: RoomRepository,
        private val memberRepository: MemberRepository,
        private val accountRepository: AccountRepository) : IRoomRepository {
    override fun getNewId(): RoomId {
        return RoomId(UUID.randomUUID())
    }

    override fun save(room: Room): RoomId {

        var roomSaved = roomRepository.findByIdOrNull(room.id.value)

        if (roomSaved == null) {
            roomSaved = roomRepository.save( room.to() )
        }

        room.members.forEach { memberEntity ->

            val memberSaved = memberRepository.findByAccount_IdAndRoom_Id(memberEntity.id.value, roomSaved.id).firstOrNull()
            if (memberSaved != null) {

                val savedThrows = memberSaved.diceThrows


                val diceThrowsToSave = memberEntity.diceThrows.filter{
                    newDiceThrow -> savedThrows.none{
                        savedThrow -> savedThrow.throwDate == newDiceThrow.throwDate
                    }
                }

                memberSaved.diceThrows += diceThrowsToSave.map { dice -> dice.to(memberSaved) }
                memberSaved.isAuthor = memberEntity.isAuthor

                memberRepository.save(memberSaved)
            } else {
                accountRepository.findByIdOrNull(memberEntity.id.value)?.let { accountModel ->
                    val newMemberModel = MemberModel(memberEntity.id.value, memberEntity.isAuthor, accountModel, roomSaved, listOf() )
                    newMemberModel.diceThrows = memberEntity.diceThrows.map { dice -> dice.to(newMemberModel) }

                    memberRepository.save(newMemberModel)
                }

            }
        }


        //roomRepository.saveAndFlush(room.to())
        return RoomId(UUID.randomUUID())
    }
}