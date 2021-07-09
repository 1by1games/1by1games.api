package com.esgi.onebyone.infrastructure.repositories

import com.esgi.onebyone.application.rooms.repositories.IRoomRepository
import com.esgi.onebyone.domain.room.Member
import com.esgi.onebyone.domain.room.Room
import com.esgi.onebyone.domain.room.RoomId
import com.esgi.onebyone.infrastructure.mappers.to
import com.esgi.onebyone.infrastructure.models.MemberModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoomRepositoryAdapter @Autowired constructor(
    private val roomRepository: RoomRepository,
    private val memberRepository: MemberRepository,
    private val accountRepository: AccountRepository
) : IRoomRepository {
    override fun getNewId(): RoomId {
        return RoomId(UUID.randomUUID())
    }

    override fun save(room: Room): RoomId {

        var roomSaved = roomRepository.findByIdOrNull(room.id.value)

        if (roomSaved == null) {
            roomSaved = roomRepository.save(room.to())
        }

        room.members.forEach { memberEntity ->
            val memberSaved =
                memberRepository.findById(memberEntity.id.value)
            if (memberSaved.isPresent) {
                val memberEntityToSave = memberSaved.get()
                val savedThrows = memberEntityToSave.diceThrows
                val diceThrowsToSave = memberEntity.diceThrows.filter { newDiceThrow ->
                    savedThrows.none { savedThrow ->
                        savedThrow.throwDate == newDiceThrow.throwDate
                    }
                }

                memberEntityToSave.diceThrows += diceThrowsToSave.map { dice -> dice.to(memberEntityToSave) }
                memberEntityToSave.isAuthor = memberEntity.isAuthor
                memberRepository.save(memberEntityToSave)
            } else {
                accountRepository.findByIdOrNull(memberEntity.id.value)?.let { accountModel ->
                    val newMemberModel =
                        MemberModel(UUID.randomUUID(), memberEntity.isAuthor, accountModel, roomSaved, listOf())
                    newMemberModel.diceThrows = memberEntity.diceThrows.map { dice -> dice.to(newMemberModel) }
                    memberRepository.save(newMemberModel)
                }
            }
        }

        val membersToRemove = roomSaved.members.filter { savedMember ->
            !(room.members.map { member -> member.username }.contains(savedMember.account?.username))
        }

        membersToRemove.forEach {
            memberRepository.delete(it)
        }

        return RoomId(roomSaved.id)
    }

    override fun doesRoomExistByNameAndState(name: String, state: Room.State): Boolean {
        return roomRepository.getByNameAndState(name, state).firstOrNull() != null
    }

    override fun findById(id: RoomId): Room? {
        return roomRepository.findByIdOrNull(id.value)?.to()
    }

    override fun findMemberByAccountIdAndRoomId(accountId: UUID, roomId: UUID): Member? {
        return memberRepository.findByAccount_IdAndRoom_Id(accountId, roomId).firstOrNull()?.to()
    }

    override fun findMembersByRoomId(roomId: UUID): List<Member> {
        return memberRepository.findByRoom_Id(roomId).map { memberModel -> memberModel.to() }
    }

    override fun findByName(roomName: String): Room? {
        return roomRepository.getByName(roomName)?.to()
    }
}