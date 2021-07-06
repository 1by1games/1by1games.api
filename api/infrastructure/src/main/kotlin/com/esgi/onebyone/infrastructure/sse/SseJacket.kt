package com.esgi.onebyone.infrastructure.sse

import com.esgi.onebyone.application.sse.SseEventType

class SseJacket(val type: SseEventType, val data: String)