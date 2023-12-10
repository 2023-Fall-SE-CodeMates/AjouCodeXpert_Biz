package codemates.ajoucodexpert.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Entity
@Table(name = "CourseMemberJoin")
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class CourseMemberJoin extends BaseEntity {
    @Embeddable
    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter
    public static class JoinId implements Serializable {
        @Column(name = "crs_id")
        private Long courseId;
        @Column(name = "usr_id", length = 30)
        private String memberId;
    }

    @EmbeddedId
    private JoinId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("courseCode")
    @JoinColumn(name = "crs_id", referencedColumnName = "crs_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("memberId")
    @JoinColumn(name = "usr_id", referencedColumnName = "usr_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "crs_role", referencedColumnName = "crs_role_code")
    private CourseMemberRole role;

    @Column(name = "is_hidden")
    private Boolean hidden;
}
