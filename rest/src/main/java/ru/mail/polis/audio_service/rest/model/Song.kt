package ru.mail.polis.audio_service.rest.model


data class Song(var id: String,
                var name: String,
                var artist: String,
                var genre: String,
                var year: Int,
                var album: String,
                var text: String,
                var duration: Int,
                var reference: String,
                var available: Boolean)

