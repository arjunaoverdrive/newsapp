package org.arjunaoverdrive.newsapp.exception;

public class CannotSaveEntityException extends RuntimeException{

    public CannotSaveEntityException(String message) {
        super(message);
    }
}
