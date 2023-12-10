package codemates.ajoucodexpert.dto;

import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.enums.Role;
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
    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Info {
        private String id;
        private String pw;
        private String name;
        private String studentCode;
        private String majorCode;
        private String majorName;
        private Integer roleCode;
        private String roleName;

        public static Info of(Member member) {

            return Info.builder()
                    .id(member.getLoginId())
                    .pw(null)
                    .name(member.getName())
                    .studentCode(member.getStudentInfo().getCode())
                    .majorCode(member.getMajor().getCode())
                    .majorName(member.getMajor().getName())
                    .roleCode(member.getAuthorities().stream().toList().get(0).getCode())
                    .roleName(Role.valueOf(member.getAuthorities().stream().toList().get(0).getCode()).getKorName())
                    .build();
        }
    }
}
