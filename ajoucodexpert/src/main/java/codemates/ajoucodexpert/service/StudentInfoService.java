package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.StudentInfo;

public interface StudentInfoService {
    // DB에서 학번으로 StudentInfo를 찾는 메서드
    StudentInfo findByStudentId(String studentId);
}
