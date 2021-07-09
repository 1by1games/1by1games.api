package com.esgi.onebyone.domain.room

import com.esgi.onebyone.domain.account.AccountID
import com.esgi.onebyone.domain.commons.exceptions.DomainException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*


class RoomTest {

    lateinit var roomAuthor: Member
    lateinit var roomDefaultDice: Dice
    lateinit var memberDefaultThrow: DiceResult
    lateinit var defaultRoom: Room
    lateinit var newMember: Member
    lateinit var newMemberUserId: UUID
    lateinit var roomAuthorId: UUID

    @BeforeEach
    fun init() {
        this.roomAuthorId = UUID.randomUUID()
        roomAuthor =
            Member(AccountID(UUID.randomUUID()), "gevinak", true, sortedSetOf<DiceResult>(), roomAuthorId)
        roomDefaultDice = Dice(6, 2)
        defaultRoom = Room.create(
            id = RoomId(UUID.randomUUID()),
            name = "myroom",
            password = "password",
            author = roomAuthor,
            roomSize = 5,
            currentDice = roomDefaultDice
        )
        this.newMemberUserId = UUID.randomUUID()
        newMember = Member(
            id = AccountID(UUID.randomUUID()),
            username = "toto",
            isAuthor = false,
            diceThrows = sortedSetOf<DiceResult>(),
            accountId = newMemberUserId,
        )
        memberDefaultThrow = DiceResult(
            dice = roomDefaultDice,
            result = roomDefaultDice.size,
            throwDate = LocalDateTime.now(),
        )
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
            currentDice = roomDefaultDice
        )
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


    //region leave room
    @Test
    fun cannot_leave_room_if_not_in() {
        Assertions.assertThrows(DomainException::class.java) {
            defaultRoom.removeMember(newMember)
        }
    }

    @Test
    fun cannot_leave_room_if_room_closed() {
        defaultRoom.addMember(newMember)
        defaultRoom.closeRoom()

        Assertions.assertThrows(DomainException::class.java) {
            defaultRoom.removeMember(newMember)
        }
    }

    @Test
    fun author_cannot_leave_room() {
        Assertions.assertThrows(DomainException::class.java) {
            defaultRoom.removeMember(roomAuthor)
        }
    }

    //endregion

    //region test password

    @Test
    fun throw_exception_on_bad_password() {
        Assertions.assertThrows(DomainException::class.java) {
            defaultRoom.isPasswordGood("passpasword")
        }
    }

    @Test
    fun shall_not_do_anything_on_password_good() {
        defaultRoom.isPasswordGood("password")
    }

    //endregion

    //region throw dice

    @Test
    fun throw_dice_work() {
        defaultRoom.addThrowToMember(memberDefaultThrow, roomAuthor)
    }

    @Test
    fun cannot_throw_dice_on_closed_room() {
        defaultRoom.closeRoom()
        Assertions.assertThrows(DomainException::class.java) {
            defaultRoom.addThrowToMember(memberDefaultThrow, roomAuthor)
        }
    }

    @Test
    fun cannot_throw_if_member_not_in_room() {
        Assertions.assertThrows(DomainException::class.java) {
            defaultRoom.addThrowToMember(memberDefaultThrow, newMember)
        }
    }

    //endregion

    //region Query room

    @Test
    fun can_not_get_room_members_if_not_room_member() {
        Assertions.assertThrows(DomainException::class.java) {
            defaultRoom.canUserGetInfo(newMemberUserId)
        }
    }


    @Test
    fun can_get_room_infos() {
        defaultRoom.canUserGetInfo(roomAuthorId)
    }

    //endregion

}