package io.github.marcoant07.ms_event_manager.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {

    private String message;
    private String method;
    private String path;
    private String statusText;
    private int status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    public ErrorMessage() {
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message){
        this.message = message;
        this.method = request.getMethod();
        this.path = request.getRequestURI();
        this.statusText = status.getReasonPhrase();
        this.status = status.value();
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult bindingResult){
        this.message = message;
        this.method = request.getMethod();
        this.path = request.getRequestURI();
        this.statusText = status.getReasonPhrase();
        this.status = status.value();
        addErrors(bindingResult);
    }

    private void addErrors(BindingResult bindingResult) {
        this.errors = new HashMap<>();

        for (FieldError fieldError: bindingResult.getFieldErrors()){
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
