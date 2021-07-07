package com.esgi.onebyone.infrastructure.sse

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface SseSubscriptionHandler {
    fun subscribeToSse(id: String): SseEmitter?
}