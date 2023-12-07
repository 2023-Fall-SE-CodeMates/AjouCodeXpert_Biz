package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.UserRequest;

public interface UserRequestResolver {
    void accept(UserRequest userRequest);
    void reject(UserRequest userRequest);
}
