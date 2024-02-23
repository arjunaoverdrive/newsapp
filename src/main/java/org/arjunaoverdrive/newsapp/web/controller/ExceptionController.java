package org.arjunaoverdrive.newsapp.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.newsapp.exception.*;
import org.arjunaoverdrive.newsapp.web.dto.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFound(UserNotFoundException e){
        log.warn("User not found or doesn't exist.", e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(EntityNotFoundException e){
        log.warn("Entity not found or doesn't exist.", e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(CannotSaveEntityException.class)
    public ResponseEntity<ErrorResponse> cannotSaveEntity(CannotSaveEntityException e){
        log.warn("Entity not found or doesn't exist.", e);

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(CannotEditEntityException.class)
    public ResponseEntity<ErrorResponse> cannotEditEntity(CannotEditEntityException e){
        log.warn(e.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(UpdateStateException.class)
    public ResponseEntity<ErrorResponse> tooLateTooUpdate(UpdateStateException e){
        log.warn(e.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(e.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> notValid(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        String errorMessage = String.join("; ", errorMessages);
        return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
    }
}
