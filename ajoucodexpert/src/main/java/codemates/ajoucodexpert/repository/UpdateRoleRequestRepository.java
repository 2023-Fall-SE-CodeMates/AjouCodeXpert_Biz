package codemates.ajoucodexpert.repository;

import codemates.ajoucodexpert.domain.UpdateRoleRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UpdateRoleRequestRepository extends UserRequestRepository<UpdateRoleRequest> {
    List<UpdateRoleRequest> findAllByRequestStatus(int requestStatus);
}
