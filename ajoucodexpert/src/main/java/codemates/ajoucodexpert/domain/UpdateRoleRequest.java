package codemates.ajoucodexpert.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "RoleRequest")
@AllArgsConstructor
@Getter
public class UpdateRoleRequest extends AdminRequest {

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "auth_code", referencedColumnName = "auth_code")
    private Authority authority;

    public UpdateRoleRequest(Member requester, LocalDateTime requestTime, Authority authority) {
        super(requester, requestTime);
        this.authority = authority;
    }

    public UpdateRoleRequest(Member requester, Authority authority) {
        super(requester, LocalDateTime.now());
        this.authority = authority;
    }

    public UpdateRoleRequest() {
        super();
    }
}
