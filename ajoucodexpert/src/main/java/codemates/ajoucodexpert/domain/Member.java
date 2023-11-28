package codemates.ajoucodexpert.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "Member")
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @Column(name="usr_id", length=30)
    private String loginId;

    @Column(name="usr_pw", nullable = false) // default length = 255
    private String pw;

    @Column(name="usr_name", nullable = false, length=30)
    private String name;

    @Column(name="usr_code", nullable = false, length=9)
    private String studentCode;

}