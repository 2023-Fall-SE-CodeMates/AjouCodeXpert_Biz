package codemates.ajoucodexpert.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "Auth")
@Getter @Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Authority implements GrantedAuthority {
    /**
     * 권한 코드
     * 0 : 관리자
     * 1 : TA
     * 2 : 학생
     */
    @Id
    @Column(name = "auth_code", nullable = false)
    private Integer code;

    @Column(name = "auth_name", nullable = false, length = 30)
    private String name;

    @Override
    public String getAuthority() {
        return null;
    }
}
