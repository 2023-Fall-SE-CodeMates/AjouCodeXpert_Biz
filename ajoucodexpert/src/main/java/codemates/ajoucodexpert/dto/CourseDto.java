package codemates.ajoucodexpert.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CourseDto {
    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Create {
        private String subjectCode;
        private String subjectName;
    }

    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Element {
        private Long courseId;
        private String subjectCode;
        private String subjectName;
        private boolean deletable;
    }
}
