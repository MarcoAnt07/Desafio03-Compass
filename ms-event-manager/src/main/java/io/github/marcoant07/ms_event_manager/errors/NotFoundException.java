package io.github.marcoant07.ms_event_manager.errors;

public class NotFoundException extends RuntimeException{
    public NotFoundException(){
        super("Entity not found");
    }
}
