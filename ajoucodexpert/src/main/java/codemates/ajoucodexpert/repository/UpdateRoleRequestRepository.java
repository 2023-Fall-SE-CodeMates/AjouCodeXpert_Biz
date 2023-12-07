package codemates.ajoucodexpert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UpdateRoleRequest extends JpaRepository<UpdateRoleRequest, Long> {
    List<UpdateRoleRequest> findAllByRequestStatusEquals(int requestStatus);
}
