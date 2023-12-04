package codemates.ajoucodexpert.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "StudentInfo")
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class StudentInfo {
    @Id @Column(name = "std_code", length = 9)
    private String code;
    @Column(name = "std_name", length = 30)
    private String name;
}
