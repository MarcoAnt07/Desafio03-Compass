package io.github.marcoant07.ms_event_manager.exception.throwable;

public class ConflictException extends RuntimeException{
    public ConflictException() {
        super("There are tickets registered for this event");
    }
}
