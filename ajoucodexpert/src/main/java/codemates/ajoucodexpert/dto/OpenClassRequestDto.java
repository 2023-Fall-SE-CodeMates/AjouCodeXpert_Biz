package codemates.ajoucodexpert.dto;

import codemates.ajoucodexpert.domain.OpenClassRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class OpenClassRequestDto {
    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Element {
        private Long requestId;
        private String subjectCode;
        private String subjectName;
        private String requesterId;
        private String requesterName;
        private LocalDateTime requestDate;

        public static Element of(OpenClassRequest entity) {
            return Element.builder()
                    .requestId(entity.getId())
                    .subjectCode(entity.getCourseCode())
                    .subjectName(entity.getCourseName())
                    .requesterId(entity.getRequester().getLoginId())
                    .requesterName(entity.getRequester().getName())
                    .requestDate(entity.getRequestTime())
                    .build();
        }
    }
}
