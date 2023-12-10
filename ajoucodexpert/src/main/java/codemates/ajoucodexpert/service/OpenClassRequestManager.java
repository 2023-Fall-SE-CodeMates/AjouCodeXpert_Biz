package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.domain.OpenClassRequest;
import codemates.ajoucodexpert.dto.CourseDto;
import codemates.ajoucodexpert.repository.OpenClassRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OpenClassRequestManager implements UserRequestService{
    private final OpenClassRequestRepository openClassRequestRepository;

    public OpenClassRequest createRequest(Member requester, CourseDto.Create dto) {
        log.info("반 개설 시작 : {} -> {}", requester.getLoginId(), dto.getSubjectCode());
        OpenClassRequest newRequest = new OpenClassRequest(requester, dto.getSubjectCode(), dto.getSubjectName());
        return openClassRequestRepository.save(newRequest);
    }

    public OpenClassRequest getRequest(Long id) {
        return openClassRequestRepository.findById(id).orElse(null);
    }

    public List<OpenClassRequest> getUnResolvedRequest() {
        return openClassRequestRepository.findAllByRequestStatus(0);
    }
}
