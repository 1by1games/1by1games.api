package com.esgi.onebyone.infrastructure.sse

import com.esgi.onebyone.application.sse.SseEmissionType
import com.esgi.onebyone.application.sse.SseEmissionType.HEARTBEAT
import com.esgi.onebyone.application.sse.SseEmitterHandler
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter


@Component
class SseSubscriptionHandlerImpl : SseSubscriptionHandler, SseEmitterHandler {

    val poolSse: HashMap<String, MutableList<SseEmitter>> = HashMap()
    val ghostSseConnection: HashMap<String, MutableList<SseEmitter>> = HashMap()
    private val mapper: ObjectMapper = ObjectMapper()

    init {
        mapper.registerModule(JavaTimeModule())
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    override fun subscribeToSse(id: String): SseEmitter {
        val emitter = SseEmitter(-1L)

        if (!poolSse.containsKey(id)) {
            poolSse[id] = mutableListOf()
        }

        poolSse[id]?.add(emitter)
        return emitter
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

    private fun emit(emitter: SseEmitter, userId: String, emission: String, emissionType: SseEmissionType) {
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

    override fun <T> emitTo(userId: String, objectEmitter: T, emissionType: SseEmissionType) {
        val objectSerialized = mapper.writeValueAsString(objectEmitter)

        if (poolSse.containsKey(userId)) {
            for (emitter in poolSse[userId]!!) {
                this.emit(emitter, userId, objectSerialized, emissionType)
            }
        }
        this.cleanEmitters()
    }
}