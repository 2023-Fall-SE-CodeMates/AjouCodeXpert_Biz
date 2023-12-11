package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Homework;
import codemates.ajoucodexpert.domain.Problem;
import codemates.ajoucodexpert.domain.TestCase;
import codemates.ajoucodexpert.dto.ProblemDto;
import codemates.ajoucodexpert.dto.TestCaseDto;
import codemates.ajoucodexpert.repository.ProblemRepository;
import codemates.ajoucodexpert.repository.TestCaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProblemServiceImpl {
    private final ProblemRepository problemRepository;
    private final TestCaseRepository testCaseRepository;

    public List<Problem> getProblems(Long courseId, Long homeworkIdx) {
        log.info("문제 조회 : {}", courseId);
        return problemRepository.findAllById_CourseIdAndId_HomeworkIdx(courseId, homeworkIdx);
    }

    public Problem getProblem(Long courseId, Long homeworkIdx, Long problemIdx) {
        log.info("문제 조회 : {}.{}.{}", courseId, homeworkIdx, problemIdx);
        return problemRepository.findById_CourseIdAndId_HomeworkIdxAndId_ProblemIdx(courseId, homeworkIdx, problemIdx).orElse(null);
    }

    public Problem createProblem(ProblemDto.Detail dto, Homework homework) {
        log.info("문제 생성 : {} - {}", dto.getIndex(), dto.getTitle());
        Problem.ProblemId id = new Problem.ProblemId(homework.getId().getCourseId(), homework.getId().getHomeworkIdx(), dto.getIndex());
        Problem newProblem = new Problem(id, dto.getTitle(), dto.getDescription(), dto.getPoints(), dto.getLangCode(), homework, new ArrayList<>());
        return problemRepository.save(newProblem);
    }

    public Problem updateProblem(ProblemDto.Detail dto, Problem problem) {
        log.info("문제 수정 : {}", dto.getTitle());
        problem.setTitle(dto.getTitle());
        problem.setDescription(dto.getDescription());
        problem.setScore(dto.getPoints());
        problem.setLanguage(dto.getLangCode());
        return problemRepository.save(problem);
    }

    public Problem saveProblem(Problem problem) {
        log.info("문제 저장 : {}", problem.getTitle());
        return problemRepository.save(problem);
    }

    public void deleteProblem(Problem problem) {
        log.info("문제 삭제 : {}.{}.{}", problem.getId().getCourseId(), problem.getId().getHomeworkIdx(), problem.getId().getProblemIdx());
        problemRepository.delete(problem);
    }

    // 과제에 속한 문제 인덱스 목록 조회
    public List<Long> getProblemIdxList(Long courseId, Long homeworkIdx) {
        log.info("문제 인덱스 목록 조회 : {}.{}", courseId, homeworkIdx);
        List<Long> problemIdxList = new ArrayList<>();

        for (Problem problem : problemRepository.findAllById_CourseIdAndId_HomeworkIdx(courseId, homeworkIdx)) {
            problemIdxList.add(problem.getId().getProblemIdx());
        }

        return problemIdxList;
    }

    public Long getLastProblemIdx(Long courseId, Long homeworkIdx) {
        log.info("마지막 문제 인덱스 조회 : {}.{}", courseId, homeworkIdx);

        Problem lastProblem = problemRepository.findTopById_CourseIdAndId_HomeworkIdxOrderById_HomeworkIdxDesc(courseId, homeworkIdx).orElse(null);
        return lastProblem == null ? 0L : lastProblem.getId().getProblemIdx();
    }

    public List<TestCase> createTestCases(Problem problem, List<TestCaseDto> dtoList) {
        log.info("테스트 케이스 생성 : {}", problem.getTitle());
        List<TestCase> testCaseList = new ArrayList<>();

        for (TestCaseDto dto : dtoList) {
            TestCase.TestCaseId id = new TestCase.TestCaseId(problem.getId().getCourseId(), problem.getId().getHomeworkIdx(), problem.getId().getProblemIdx(), dto.getIndex());
            TestCase newTestCase = new TestCase(id, problem, dto.getInput(), dto.getOutput());
            testCaseList.add(newTestCase);
        }

        return testCaseList;
    }

    public void updateTestCases(Problem problem, List<TestCase> testCases) {
        log.info("테스트 케이스 수정 : {}", problem.getTitle());
        //problem.setTestCases(testCases);
        // 기존 테스트케이스 모두 삭제
        testCaseRepository.deleteAllByProblem(problem);

        // 새로운 테스트케이스 저장
        testCaseRepository.saveAll(testCases);
    }
}
