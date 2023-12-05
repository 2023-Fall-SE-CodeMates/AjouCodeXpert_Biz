package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Major;
import codemates.ajoucodexpert.repository.MajorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MajorServiceImpl implements MajorService {

    private final MajorRepository majorRepository;

    @Override
    public Major findByMajorCode(String majorCode) {
        return majorRepository.findById(majorCode).orElse(null);
    }
}
