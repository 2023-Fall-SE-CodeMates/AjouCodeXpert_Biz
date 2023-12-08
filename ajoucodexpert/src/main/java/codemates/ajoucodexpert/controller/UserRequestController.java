package codemates.ajoucodexpert.controller;

import codemates.ajoucodexpert.domain.Authority;
import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.domain.UpdateRoleRequest;
import codemates.ajoucodexpert.exception.BusinessException;
import codemates.ajoucodexpert.exception.ExceptionType;
import codemates.ajoucodexpert.service.AuthorityService;
import codemates.ajoucodexpert.service.MemberService;
import codemates.ajoucodexpert.service.UpdateRoleRequestManager;
import codemates.ajoucodexpert.service.UpdateRoleRequestResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
@Slf4j
public class UserRequestController {
    private final UpdateRoleRequestResolver updateRoleRequestResolver;
    private final UpdateRoleRequestManager updateRoleRequestManager;
    private final MemberService memberService;
    private final AuthorityService authorityService;

    @PostMapping("/role")
    public ResponseEntity<Object> processUpdateRoleRequest(
            @RequestParam("requestId") Long requestId,
            @RequestParam("action") Long action,
            @AuthenticationPrincipal User user) {

        log.debug("회원 권한 변경 요청 처리 : {} -> {}", user.getUsername(), requestId);
        UpdateRoleRequest request = updateRoleRequestManager.getRequest(requestId);

        if (request == null) throw new BusinessException(ExceptionType.DATA_NOT_FOUND, "존재하지 않는 요청 아이디입니다.");

        if (action == 1) {
            updateRoleRequestResolver.accept(request);
        }
        else if (action == -1) {
            updateRoleRequestResolver.reject(request);
        }
        else {
            throw new BusinessException(ExceptionType.INVALID_INPUT, "권한 변경 요청 인자가 잘못되었습니다.");
        }

        return ResponseEntity.noContent().build();
    }



}
