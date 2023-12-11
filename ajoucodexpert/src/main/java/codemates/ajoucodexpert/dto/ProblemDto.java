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
        private Long problemIdx;
        private String title;
        private String description;
        private Integer score;
        private Integer langCode;
        private String langName;

        public static Detail of(Problem problem) {
            String languageName = problem.getLanguage() == 0 ? "C" : problem.getLanguage() == 1 ? "JAVA" : "PYTHON";
            return Detail.builder()
                    .problemIdx(problem.getId().getProblemIdx())
                    .title(problem.getTitle())
                    .description(problem.getDescription())
                    .score(problem.getScore())
                    .langCode(problem.getLanguage())
                    .langName(languageName)
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
