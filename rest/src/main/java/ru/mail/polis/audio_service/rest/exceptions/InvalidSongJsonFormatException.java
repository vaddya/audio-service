package ru.mail.polis.audio_service.rest.exceptions;

public class InvalidSongJsonFormatException extends Exception {
    public InvalidSongJsonFormatException() {
        super("Song json has invalid format");
    }
}
