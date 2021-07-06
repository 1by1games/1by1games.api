package com.esgi.onebyone.application.rooms.throw_dice_command

import com.esgi.onebyone.application.sse.SseEmitterHandler
import io.jkratz.mediator.core.Request
import io.jkratz.mediator.core.RequestHandler
import org.springframework.stereotype.Component
import java.util.*

data class ThrowDiceCommand(val amount: Int?, val value: Int?, val roomId: UUID, val userId: UUID): Request<Unit>

@Component
class ThrowDiceCommandHandler(
    sseEmitterHandler: SseEmitterHandler
): RequestHandler<ThrowDiceCommand, Unit> {
    override fun handle(request: ThrowDiceCommand) {
        


    }

}