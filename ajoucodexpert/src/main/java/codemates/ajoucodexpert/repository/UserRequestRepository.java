package codemates.ajoucodexpert.repository;

import codemates.ajoucodexpert.domain.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRequestRepository<T extends UserRequest> extends JpaRepository<T, Long> {
}
