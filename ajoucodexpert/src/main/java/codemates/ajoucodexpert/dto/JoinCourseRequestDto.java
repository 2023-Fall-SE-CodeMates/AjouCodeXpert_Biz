package codemates.ajoucodexpert.dto;

import codemates.ajoucodexpert.domain.CourseMemberJoin;
import codemates.ajoucodexpert.domain.JoinCourseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class JoinCourseRequestDto {
    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Request {
        private String joinCode;
    }

    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Element {
        private Long id;
        private String studentId;
        private String studentName;
        private Integer roleCode;
        private String roleName;
        private String loginId;
        private String majorCode;
        private String majorName;
        
        public static Element of(JoinCourseRequest request) {
            return Element.builder()
                    .id(request.getId())
                    .studentId(request.getRequester().getStudentInfo().getCode())
                    .studentName(request.getRequester().getStudentInfo().getName())
                    .roleCode(request.getRole().getCode())
                    .roleName(request.getRole().getName())
                    .loginId(request.getRequester().getLoginId())
                    .majorCode(request.getRequester().getMajor().getCode())
                    .majorName(request.getRequester().getMajor().getName())
                    .build();
        }
    }
}
