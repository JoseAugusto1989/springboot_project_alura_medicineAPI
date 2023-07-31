package com.medicine.course.Medicine.exception;

import lombok.Getter;

@Getter
public class ObjectNotFoundException extends RuntimeException {

    private final String field;

    public ObjectNotFoundException(String message, String field) {
        super(message);
        this.field = field;
    }
}

