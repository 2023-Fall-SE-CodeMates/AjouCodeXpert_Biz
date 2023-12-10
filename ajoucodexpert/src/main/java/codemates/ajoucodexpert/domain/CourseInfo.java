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
@Table(name = "Course")
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class CourseInfo {
    @Id @Column(name = "crs_code", length = 8)
    private String code;

    @Column(name = "crs_name", length = 50)
    private String name;
}
