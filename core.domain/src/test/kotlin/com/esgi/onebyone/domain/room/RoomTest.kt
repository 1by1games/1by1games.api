package com.esgi.onebyone.domain.room

import com.esgi.onebyone.domain.account.AccountID
import com.esgi.onebyone.domain.commons.exceptions.DomainException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*


class RoomTest {

    lateinit var roomAuthor: Member
    lateinit var roomDefaultDice: Dice
    lateinit var defaultRoom: Room
    lateinit var newMember: Member

    @BeforeEach
    fun init() {
        roomAuthor = Member(AccountID( UUID.randomUUID()), "gevinak", true , sortedSetOf<DiceResult>() )
        roomDefaultDice= Dice(6, 2)
        defaultRoom = Room.create(
                id = RoomId(UUID.randomUUID()),
                name = "souffreteux",
                password = "password",
                author = roomAuthor,
                roomSize = 5,
                currentDice = roomDefaultDice)
        newMember = Member(
                id = AccountID(UUID.randomUUID()),
                username = "toto",
                isAuthor = false,
                diceThrows = sortedSetOf<DiceResult>())
    }

    @Test
    fun room_creation_works() {

        Assertions.assertEquals(roomAuthor.id, defaultRoom.author.id)
    }


    //region join room

    @Test
    fun can_join_room() {
        defaultRoom.addMember(newMember)
        Assertions.assertEquals(2, defaultRoom.members.size)
    }


    @Test
    fun cannot_join_full_room() {
        val smallRoom = Room.create(
            id = RoomId(UUID.randomUUID()),
            name = "souffreteux",
            password = "password",
            author = roomAuthor,
            roomSize = 1,
            currentDice = roomDefaultDice)
        Assertions.assertThrows(DomainException::class.java) {
            smallRoom.addMember(newMember)
        }
    }

    @Test
    fun cannot_join_room_twice() {
        defaultRoom.addMember(newMember)
        Assertions.assertThrows(DomainException::class.java) {
            defaultRoom.addMember(newMember)
        }
    }

    @Test
    fun cannot_join_closed_room() {
        defaultRoom.closeRoom()

        Assertions.assertThrows(DomainException::class.java) {
            defaultRoom.addMember(newMember)
        }
    }

    //endregion



}