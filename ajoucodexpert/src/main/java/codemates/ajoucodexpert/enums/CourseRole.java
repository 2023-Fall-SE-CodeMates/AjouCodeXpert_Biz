package codemates.ajoucodexpert.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CourseRole {
    CREATOR(0, "CREATOR", "개설자"),
    TA(1, "TA", "TA"),
    STUDENT(2, "STUDENT", "학생");

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
}
