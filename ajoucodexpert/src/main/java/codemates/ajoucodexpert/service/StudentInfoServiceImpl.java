package codemates.ajoucodexpert.service;


import codemates.ajoucodexpert.domain.StudentInfo;
import codemates.ajoucodexpert.repository.StudentInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudentInfoServiceImpl implements StudentInfoService{

    private final StudentInfoRepository studentInfoRepository;
    @Override
    public StudentInfo findByStudentId(String studentId) {
        return studentInfoRepository.findById(studentId).orElse(null);
    }
}
