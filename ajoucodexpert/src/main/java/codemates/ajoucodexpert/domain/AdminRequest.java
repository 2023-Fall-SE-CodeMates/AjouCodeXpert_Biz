package codemates.ajoucodexpert.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class AdminRequest extends UserRequest{

    public AdminRequest(Member requester, LocalDateTime requestTime) {
        super(requester, requestTime, unprocessed, null);
    }
    @Override
    void accept() {
        this.setRequestStatus(accepted);
        this.process();
    }

    @Override
    void reject() {
        this.setRequestStatus(rejected);
        this.process();
    }

    @Override
    void process() {
        this.setCompleteTime(LocalDateTime.now());
    }
}
