package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Course;
import codemates.ajoucodexpert.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public Course createCourse(Course newCourse) {
        log.info("대회 개설 시작 : {}", newCourse.getId());
        if (newCourse.getJoinCode() == null) {
            String generatedString = getRandomNumericAndAlphabeticString();

            newCourse.generateJoinCode(generatedString);
        }
        return courseRepository.save(newCourse);
    }

    private static String getRandomNumericAndAlphabeticString() {
        // 임의의 9자리 문자열을 생성
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 9;
        Random random = new Random();

        return random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }
}
