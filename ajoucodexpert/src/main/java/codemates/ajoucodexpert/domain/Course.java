package codemates.ajoucodexpert.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Course")
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Course extends BaseEntity{
    @Id @Column(name = "crs_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "crs_code", length = 10, nullable = false) @NonNull
    private String code;
    @Column(name = "crs_name", length = 50, nullable = false) @NonNull
    private String name;
    @Column(name = "crs_join_code", length = 9, nullable = false, unique = true)
    private String joinCode;

    public void generateJoinCode(String joinCode) {
        this.joinCode = joinCode;
    }

}
