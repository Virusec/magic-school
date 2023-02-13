package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundMyException extends RuntimeException {
    public EntityNotFoundMyException(String message) {
        super(message);
    }
}
