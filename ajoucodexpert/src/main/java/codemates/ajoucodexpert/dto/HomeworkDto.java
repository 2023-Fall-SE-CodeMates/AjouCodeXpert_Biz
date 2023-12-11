package codemates.ajoucodexpert.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class HomeworkDto {
    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Create {
        private Long homeworkIdx;
        private String title;
        private String content;
        private LocalDateTime endDate;

    }

    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Element {
        private Long homeworkId;
        private String title;
        private LocalDateTime createdDate;
        private LocalDateTime endDate;
        private Boolean removable;
    }
}
