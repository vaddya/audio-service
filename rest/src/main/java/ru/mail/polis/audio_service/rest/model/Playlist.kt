package ru.mail.polis.audio_service.rest.model


data class Playlist(var id: String,
                    var name: String,
                    var songs: List<Song>,
                    var available: Boolean)