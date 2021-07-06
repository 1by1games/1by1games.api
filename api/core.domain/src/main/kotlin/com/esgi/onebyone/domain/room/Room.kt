package com.esgi.onebyone.domain.room

import com.esgi.onebyone.domain.commons.exceptions.DomainException
import java.time.LocalDateTime
import java.time.ZoneOffset


class Room(
    val id: RoomId,
    val name: String,
    val password: String,
    private val _members: MutableList<Member>,
    var state: Room.State,
    val creationDate: LocalDateTime,
    val endDate: LocalDateTime?,
    val roomSize: Int,
    val currentDice: Dice

) {

    enum class State(state: String) {
        OPEN("Open"),
        CLOSED("Closed")
    }

    val author: Member get() = members.first { member -> member.isAuthor }

    val members: List<Member> get() = _members


    companion object {
        fun create(
            id: RoomId,
            name: String,
            password: String,
            author: Member,
            roomSize: Int,
            currentDice: Dice
        ): Room {

            val members = mutableListOf<Member>()
            author.isAuthor = true
            members.add(author)
            return Room(
                id,
                name,
                password,
                members,
                State.OPEN,
                LocalDateTime.now(ZoneOffset.UTC),
                null,
                roomSize,
                currentDice
            )
        }
    }

    fun addMember(newMember: Member) {
        if (members.any { member -> member.username === newMember.username }) {
            throw DomainException("member already in room")
        }
        if (members.size >= roomSize) {
            throw DomainException("room is full")
        }

        if (state == State.CLOSED) {
            throw DomainException("room is closed")
        }
        _members.add(newMember)
    }

    fun closeRoom() {
        state = State.CLOSED
    }

    fun removeMember(removedMember: Member) {
        if (members.none { member -> member.username === removedMember.username }) {
            throw DomainException("member not in room")
        }

        if (state == State.CLOSED) {
            throw DomainException("room is closed")
        }

        if (author.username == removedMember.username) {
            throw DomainException("Author can't leave the room")
        }

        _members.removeAt(_members.indexOf(_members.find { member -> member.username == removedMember.username }))
    }

    fun isPasswordGood(testedPassword: String) {
        if(password !== testedPassword) {
            throw throw DomainException("Password invalid")
        }
    }
}