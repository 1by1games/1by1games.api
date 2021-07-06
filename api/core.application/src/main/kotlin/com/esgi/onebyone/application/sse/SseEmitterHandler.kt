package com.esgi.onebyone.application.sse

interface SseEmitterHandler {
    fun emitTo(userId: String, gameEventSerialized: String, emissionType: SseEventType)
}