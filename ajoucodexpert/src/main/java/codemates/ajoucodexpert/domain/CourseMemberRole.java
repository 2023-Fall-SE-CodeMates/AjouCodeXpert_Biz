package codemates.ajoucodexpert.domain;

import codemates.ajoucodexpert.enums.CourseRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CourseMemberRole")
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class CourseMemberRole {
    @Id @Column(name = "crs_role_code")
    private Integer code;  // 0 : 개설자, 1 : TA, 2 : 학생
    @Column(name = "crs_role_name", length = 30)
    private String name;

    public CourseMemberRole(CourseRole roleEnum) {
        this(roleEnum.getCode(), roleEnum.getName());
    }
}
