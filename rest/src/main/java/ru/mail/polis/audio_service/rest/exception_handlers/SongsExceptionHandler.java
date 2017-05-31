package ru.mail.polis.audio_service.rest.exception_handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.mail.polis.audio_service.rest.exceptions.InvalidSongJsonFormatException;
import ru.mail.polis.audio_service.rest.exceptions.SongAlreadyExistsException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static ru.mail.polis.audio_service.rest.exception_handlers.UtilKt.message;

@RestControllerAdvice
public class SongsExceptionHandler {

    @ExceptionHandler(InvalidSongJsonFormatException.class)
    @ResponseStatus(CONFLICT)
    public ResponseMessage invalidSongJson(InvalidSongJsonFormatException e) {
        return message(CONFLICT, e);
    }

    @ExceptionHandler(SongAlreadyExistsException.class)
    @ResponseStatus(CONFLICT)
    public ResponseMessage songAlreadyExists(SongAlreadyExistsException e) {
        return message(CONFLICT, e);
    }

}
