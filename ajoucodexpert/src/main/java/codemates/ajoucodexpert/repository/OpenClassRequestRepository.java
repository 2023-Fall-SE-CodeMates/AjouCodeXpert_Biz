package codemates.ajoucodexpert.repository;

import codemates.ajoucodexpert.domain.OpenClassRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpenClassRequestRepository extends UserRequestRepository<OpenClassRequest> {
    List<OpenClassRequest> findAllByRequestStatus(int requestStatus);
}
