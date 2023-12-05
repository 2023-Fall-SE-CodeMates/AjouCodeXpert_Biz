package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Authority;
import codemates.ajoucodexpert.repository.AuthorityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthorityServiceImpl implements AuthorityService{
    private final AuthorityRepository authorityRepository;

    @Override
    public Authority getAuthorityByCode(Integer code) {
        return authorityRepository.findByCode(code).orElse(null);
    }
}
