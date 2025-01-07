package io.github.marcoant07.ms_ticket_manager.exception;

import io.github.marcoant07.ms_ticket_manager.exception.throwable.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler{

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handlerNotFoundException(NotFoundException e,HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(request, HttpStatus.NOT_FOUND, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumenteNotValidException(MethodArgumentNotValidException e, HttpServletRequest request, BindingResult bindingResult){
        ErrorMessage errorMessage = new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Invalid Fields", bindingResult);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }
}
