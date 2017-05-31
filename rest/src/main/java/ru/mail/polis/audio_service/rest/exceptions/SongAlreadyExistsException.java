package ru.mail.polis.audio_service.rest.exceptions;

public class SongAlreadyExistsException extends Exception {
    private static final String MSG = "Song with such id already exists: ";

    public SongAlreadyExistsException(String id) {
        super(MSG + id);
    }
}
