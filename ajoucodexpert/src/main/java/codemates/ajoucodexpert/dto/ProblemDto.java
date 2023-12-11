package codemates.ajoucodexpert.dto;

import codemates.ajoucodexpert.domain.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProblemDto {
    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Detail {
        private Long index;
        private String title;
        private String description;
        private Integer points;
        private Integer langCode;
        private String langName;
        private Boolean removable;

        public static Detail of(Problem problem) {
            String languageName = problem.getLanguage() == 0 ? "C" : problem.getLanguage() == 1 ? "JAVA" : "PYTHON";
            return Detail.builder()
                    .index(problem.getId().getProblemIdx())
                    .title(problem.getTitle())
                    .description(problem.getDescription())
                    .points(problem.getScore())
                    .langCode(problem.getLanguage())
                    .langName(languageName)
                    .removable(false)
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Element {
        private Long problemIdx;
        private Boolean removable;
    }
}
