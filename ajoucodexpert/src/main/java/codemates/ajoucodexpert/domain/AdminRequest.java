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
    public void accept() {
        this.setRequestStatus(accepted);
        this.process();
    }

    @Override
    public void reject() {
        this.setRequestStatus(rejected);
        this.process();
    }

    @Override
    public void process() {
        this.setCompleteTime(LocalDateTime.now());
    }
}
