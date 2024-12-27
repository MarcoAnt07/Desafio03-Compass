package io.github.marcoant07.ms_ticket_manager.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmailDTO {

    private String emailTo;
    private String subject;
    private String text;

    public EmailDTO() {
    }

    @JsonCreator
    public EmailDTO(@JsonProperty("emailTo") String emailTo,
                    @JsonProperty("subject") String subject,
                    @JsonProperty("text") String text) {
        this.emailTo = emailTo;
        this.subject = subject;
        this.text = text;
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
