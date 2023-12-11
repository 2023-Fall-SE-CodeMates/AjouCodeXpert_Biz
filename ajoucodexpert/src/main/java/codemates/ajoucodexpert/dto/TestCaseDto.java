package codemates.ajoucodexpert.dto;

import codemates.ajoucodexpert.domain.TestCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class TestCaseDto {
    private Long index;
    private String input;
    private String output;
}
