package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Authority;
import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.domain.UpdateRoleRequest;
import codemates.ajoucodexpert.repository.UpdateRoleRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UpdateRoleRequestManager implements UserRequestService {
    private final UpdateRoleRequestRepository updateRoleRequestRepository;

    public UpdateRoleRequest createRequest(Member member, Authority authority) {
        UpdateRoleRequest updateRoleRequest = new UpdateRoleRequest(member, authority);
        return updateRoleRequestRepository.save(updateRoleRequest);
    }
}
