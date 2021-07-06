package com.esgi.onebyone.infrastructure.sse

import com.esgi.onebyone.application.sse.SseEventType
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface SseHandler {
    fun subscribeToSse(id: String): SseEmitter?
    fun emitTo(userId: String, gameEventSerialized: String, emissionType: SseEventType)
}