package io.github.marcoant07.ms_ticket_manager.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Email {

    private String emailTo;
    private String subject;
    private String text;

    public enum StatusEmail{
        SENT,
        ERROR
    }

    public Email() {
    }

    @JsonCreator
    public Email(@JsonProperty("emailTo") String emailTo,
                 @JsonProperty("subject") String subject,
                 @JsonProperty("text") String text) {
        this.emailTo = emailTo;
        this.text = text;
        this.subject = subject;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
