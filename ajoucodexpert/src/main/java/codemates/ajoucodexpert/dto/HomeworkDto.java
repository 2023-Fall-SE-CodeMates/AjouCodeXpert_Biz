package codemates.ajoucodexpert.dto;

import codemates.ajoucodexpert.domain.Homework;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HomeworkDto {
    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Create {
        private Long homeworkIdx;
        private String title;
        private String content;
        private LocalDateTime endDate;
        private List<ProblemDto.Detail> problems;

    }

    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Detail {
        private Long homeworkIdx;
        private String title;
        private String content;
        private LocalDateTime createdDate;
        private LocalDateTime endDate;
        private Boolean removable;
        private List<ProblemDto.Detail> problems;

        public static Detail of(Homework homework) {
            return Detail.builder()
                    .homeworkIdx(homework.getId().getHomeworkIdx())
                    .title(homework.getTitle())
                    .content(homework.getContent())
                    .createdDate(homework.getCourse().getCreatedDate())
                    .endDate(homework.getEndDate())
                    .removable(false)
                    .problems(new ArrayList<>())
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Element {
        private Long homeworkIdx;
        private String title;
        private LocalDateTime endDate;
        private Boolean removable;

        public static Element of(Homework homework) {
            return Element.builder()
                    .homeworkIdx(homework.getId().getHomeworkIdx())
                    .title(homework.getTitle())
                    .endDate(homework.getEndDate())
                    .removable(false)
                    .build();
        }
    }
}
