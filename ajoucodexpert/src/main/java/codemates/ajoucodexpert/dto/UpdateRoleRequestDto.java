package codemates.ajoucodexpert.dto;

import codemates.ajoucodexpert.domain.UpdateRoleRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UpdateRoleRequestDto {
    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Element {
        private Long requestId;
        private String loginId;
        private String name;
        private String studentId;
        private String majorCode;
        private String majorName;

        public static Element of(UpdateRoleRequest updateRoleRequest) {
            return Element.builder()
                    .requestId(updateRoleRequest.getId())
                    .loginId(updateRoleRequest.getRequester().getLoginId())
                    .name(updateRoleRequest.getRequester().getName())
                    .studentId(updateRoleRequest.getRequester().getStudentInfo().getCode())
                    .majorCode(updateRoleRequest.getRequester().getMajor().getCode())
                    .majorName(updateRoleRequest.getRequester().getMajor().getName())
                    .build();
        }
    }
}
