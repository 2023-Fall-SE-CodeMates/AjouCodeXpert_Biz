package codemates.ajoucodexpert.enums;

import codemates.ajoucodexpert.domain.Authority;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    ADMIN(0, "ROLE_ADMIN", "관리자"),
    TA(1, "ROLE_TA", "TA"),
    STUDENT(2, "ROLE_STUDENT", "학생");

    private final int code;
    private final String name;
    private final String korName;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getKorName() {
        return korName;
    }

    public static Role valueOf(int code) {
        for (Role role : Role.values()) {
            if (role.getCode() == code) {
                return role;
            }
        }
        return null;
    }

    public Authority toEntity() {
        return Authority.builder()
                .code(code)
                .name(name)
                .build();
    }
}
