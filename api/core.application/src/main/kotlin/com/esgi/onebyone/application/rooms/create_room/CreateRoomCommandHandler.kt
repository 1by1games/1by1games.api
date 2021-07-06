package com.esgi.onebyone.application.rooms.create_room

import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.application.accounts.repositories.IAccountsRepository
import com.esgi.onebyone.application.contracts.services.IHashingService
import com.esgi.onebyone.application.rooms.repositories.IRoomRepository
import com.esgi.onebyone.domain.account.AccountID
import com.esgi.onebyone.domain.room.Dice
import com.esgi.onebyone.domain.room.Member
import com.esgi.onebyone.domain.room.Room
import com.esgi.onebyone.domain.room.RoomId
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import java.util.*

data class CreateRoomCommand(
        var size : Int,
        var name : String,
        var password : String,
        var authorId : UUID,
        var currentDiceSize : Int,
        var currentDiceAmount : Int
        ) : Request<RoomId>

@Component
class CreateRoomCommandHandler constructor(private val repository: IRoomRepository,
                       private val accountRepository: IAccountsRepository,
                       private val hashingService: IHashingService)
    : RequestHandler<CreateRoomCommand, RoomId> {

    override fun handle(request: CreateRoomCommand): RoomId {
        if(repository.doesRoomExistByNameAndState(request.name, Room.State.OPEN)) {
            throw ApplicationException("An open room with this name: ${request.name} already exist")
        }

        var dice = Dice(request.currentDiceSize, request.currentDiceAmount)

        val authorAccount = accountRepository.findById(AccountID(request.authorId))
                ?: throw ApplicationException("User not found")

        val author = Member(
                AccountID(request.authorId),
                authorAccount.username,
                true,
                sortedSetOf()
        )

        val newRoom = Room.create(
            id = repository.getNewId(),
            name = request.name,
            password = hashingService.hashPassword(request.password),
            author = author,
            roomSize = request.size,
            currentDice = dice
        )

        return repository.save(newRoom)

    }

}