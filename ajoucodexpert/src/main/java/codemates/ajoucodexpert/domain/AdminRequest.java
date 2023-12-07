package codemates.ajoucodexpert.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class AdminRequest extends UserRequest{

    public AdminRequest(String requesterId, LocalDateTime requestTime) {
        super(requesterId, requestTime, 0, null);
    }
    @Override
    void accept() {
        this.setRequestStatus(1);
        this.process();
    }

    @Override
    void reject() {
        this.setRequestStatus(-1);
        this.process();
    }

    @Override
    void process() {
        this.setCompleteTime(LocalDateTime.now());
    }
}
