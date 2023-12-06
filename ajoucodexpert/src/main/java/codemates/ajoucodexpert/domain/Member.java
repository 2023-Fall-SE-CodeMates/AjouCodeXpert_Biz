package codemates.ajoucodexpert.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="mjr_code", referencedColumnName = "mjr_code")
    private Major major;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="std_code", referencedColumnName = "std_code")
    private StudentInfo studentInfo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name="Member_Auth",
            joinColumns = @JoinColumn(name="usr_id", referencedColumnName = "usr_id"),
            inverseJoinColumns = @JoinColumn(name="auth_code", referencedColumnName = "auth_code"))
    private Set<Authority> authorities;

}