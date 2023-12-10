package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.CourseInfo;
import codemates.ajoucodexpert.repository.CourseInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CourseInfoServiceImpl implements CourseInfoService {
    private final CourseInfoRepository courseInfoRepository;
    @Override
    public CourseInfo getCourse(String code) {
        return courseInfoRepository.findByCode(code).orElse(null);
    }
}
