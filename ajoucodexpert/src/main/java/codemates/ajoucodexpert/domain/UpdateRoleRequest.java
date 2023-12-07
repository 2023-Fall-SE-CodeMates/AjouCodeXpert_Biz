package codemates.ajoucodexpert.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "RoleRequest")
@AllArgsConstructor
@Getter
public class UpdateRoleRequest extends AdminRequest {

    public UpdateRoleRequest(String requesterId, LocalDateTime requestTime) {
        super(requesterId, requestTime);
    }
}
