package io.github.marcoant07.ms_event_manager.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(){
        super("Entity not found");
    }
}
