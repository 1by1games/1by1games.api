package com.esgi.onebyone.infrastructure.sse

import com.esgi.onebyone.application.sse.SseEventType
import com.esgi.onebyone.application.sse.SseEventType.*
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import org.springframework.stereotype.Component

@Component
class SseHandlerImpl: SseHandler {

    val poolSse: HashMap<String, MutableList<SseEmitter>> = HashMap()
    val ghostSseConnection: HashMap<String, MutableList<SseEmitter>> = HashMap()

    override fun subscribeToSse(id: String): SseEmitter {
        val emitter = SseEmitter(-1L)

        if (!poolSse.containsKey(id)) {
            poolSse[id] = mutableListOf()
        }

        poolSse[id]?.add(emitter)
        return emitter
    }

    override fun emitTo(userId: String, gameEventSerialized: String, emissionType: SseEventType) {
        if (poolSse.containsKey(userId)) {
            for (emitter in poolSse[userId]!!) {
                this.emit(emitter, userId, gameEventSerialized, emissionType)
            }
        }
        this.cleanEmitters()
    }

    private fun cleanEmitters() {
        for ((key, value) in ghostSseConnection) {
            for (emitter in value) {
                this.poolSse[key]?.remove(emitter)
            }
        }

        this.ghostSseConnection.clear()
    }

    @Scheduled(fixedRate = 30000)
    fun keepAlive() {
        for ((key, value) in poolSse) {
            for (emitter in value) {
                this.emit(emitter, key, "", HEARTBEAT)
            }
        }
        this.cleanEmitters()
    }

    private fun emit(emitter: SseEmitter, userId: String, emission: String, emissionType: SseEventType) {
        val event = SseEmitter.event()
        event.data(SseJacket(emissionType, emission))

        kotlin.runCatching {
            emitter.send(event)
        }.onFailure {
            if (!ghostSseConnection.containsKey(userId)) {
                ghostSseConnection[userId] = mutableListOf()
            }
            ghostSseConnection[userId]?.add(emitter)
        }
    }
}