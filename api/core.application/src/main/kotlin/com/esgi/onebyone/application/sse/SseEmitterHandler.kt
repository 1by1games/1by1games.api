package com.esgi.onebyone.application.sse

interface SseEmitterHandler {
    fun <T> emitTo(userId: String, objectEmitter: T, emissionType: SseEmissionType)
}