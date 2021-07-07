package com.esgi.onebyone.infrastructure.sse

import com.esgi.onebyone.application.sse.SseEmissionType

class SseJacket(val type: SseEmissionType, val data: String)