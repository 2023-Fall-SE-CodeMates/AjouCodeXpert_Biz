package codemates.ajoucodexpert.domain;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class BasicRequest extends UserRequest {
    public BasicRequest(Member requester, LocalDateTime requestTime) {
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
