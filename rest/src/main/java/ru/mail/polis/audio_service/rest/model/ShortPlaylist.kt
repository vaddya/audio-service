package ru.mail.polis.audio_service.rest.model


data class ShortPlaylist(var id: String,
                         var name: String,
                         var songsIds: List<String>,
                         var available: Boolean)