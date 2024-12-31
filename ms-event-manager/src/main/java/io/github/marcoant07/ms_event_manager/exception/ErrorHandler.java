package io.github.marcoant07.ms_event_manager.exception;

import io.github.marcoant07.ms_event_manager.exception.throwable.ConflictException;
import io.github.marcoant07.ms_event_manager.exception.throwable.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorMessage> handlerConflictException(ConflictException e, HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(request, HttpStatus.CONFLICT, e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handlerNotFoundException(NotFoundException e,HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(request, HttpStatus.NOT_FOUND, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }
}
