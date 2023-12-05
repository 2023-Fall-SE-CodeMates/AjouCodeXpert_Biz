package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Major;

public interface MajorService {
    // DB에서 전공코드로 Major를 찾는 메서드
    Major findByMajorCode(String majorCode);
}
