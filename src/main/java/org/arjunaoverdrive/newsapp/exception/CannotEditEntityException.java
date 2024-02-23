package org.arjunaoverdrive.newsapp.exception;

public class CannotEditEntityException extends RuntimeException{
    public CannotEditEntityException(String message) {
        super(message);
    }
}
