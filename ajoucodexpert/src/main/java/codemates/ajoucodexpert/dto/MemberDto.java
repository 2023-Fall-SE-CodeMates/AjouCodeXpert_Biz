package codemates.ajoucodexpert.dto;

import codemates.ajoucodexpert.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberDto {
    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Login{
        private String loginId;
        private String pw;
    }

    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Signup {
        private String id;
        private String pw;
        private String name;
        private String studentId;
        private String majorCode;
        private Integer roleCode;

    }


}
