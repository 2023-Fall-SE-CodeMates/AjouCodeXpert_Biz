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
@Table(name = "Major")
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Major {
    @Id @Column(name = "mjr_code", length = 4)
    private String code;
    @Column(name = "mjr_name", length = 30)
    private String name;
}
