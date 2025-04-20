package com.food.exception;
@SuppressWarnings("serial")
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
