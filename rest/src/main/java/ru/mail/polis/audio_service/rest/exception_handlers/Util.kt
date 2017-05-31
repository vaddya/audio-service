package ru.mail.polis.audio_service.rest.exception_handlers

import org.springframework.http.HttpStatus

fun message(status: HttpStatus, e: Exception): ResponseMessage {
    return ResponseMessage(status.value(), e.message!!)
}

