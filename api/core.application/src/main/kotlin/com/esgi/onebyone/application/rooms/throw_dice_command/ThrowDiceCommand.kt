package com.esgi.onebyone.application.rooms.throw_dice_command

import com.esgi.onebyone.application.ApplicationException
import com.esgi.onebyone.application.accounts.repositories.IAccountsRepository
import com.esgi.onebyone.application.rooms.repositories.IRoomRepository
import com.esgi.onebyone.application.sse.SseEmitterHandler
import com.esgi.onebyone.application.webClient.WebClient
import com.esgi.onebyone.domain.account.AccountID
import com.esgi.onebyone.domain.room.Dice
import com.esgi.onebyone.domain.room.DiceResult
import com.esgi.onebyone.domain.room.RoomId
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

data class ThrowDiceCommand(val amount: Int?, val value: Int?, val roomId: UUID, val userId: UUID) : Request<Unit>

@Component
class ThrowDiceCommandHandler(
    val sseEmitterHandler: SseEmitterHandler,
    private val roomRepository: IRoomRepository,
    private val accountRepository: IAccountsRepository,
    private val webClient: WebClient,
) : RequestHandler<ThrowDiceCommand, Unit> {
    override fun handle(request: ThrowDiceCommand) {
        val user = accountRepository.findById(AccountID(request.userId))
            ?: throw ApplicationException("User not found")

        val room = roomRepository.findById(RoomId(request.roomId))
            ?: throw ApplicationException("Room not found")

        val member = roomRepository.findMemberByAccountIdAndRoomId(request.userId, request.roomId)
            ?: throw ApplicationException("This user in not in the room")

        room.canThrowDice(member)


        val dice = if (!(request.amount == null || request.value == null))
            Dice(request.value, request.amount)
        else
            room.currentDice

        dice.canBeThrow()

        val throwResultFromApi = webClient.getRandomFromApi(dice.size, dice.amount)

        val throwResult = DiceResult(dice, throwResultFromApi, LocalDateTime.now())

        room.addThrowToMember(throwResult, member)

        roomRepository.save(room)


    }

}