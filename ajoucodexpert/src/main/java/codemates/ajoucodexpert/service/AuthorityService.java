package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Authority;

public interface AuthorityService {
    // code를 통해 Authority를 가져오는 메서드
    Authority getAuthorityByCode(Integer code);
}
