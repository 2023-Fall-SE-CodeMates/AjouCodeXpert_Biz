package codemates.ajoucodexpert.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "JoinRequeset")
@AllArgsConstructor @NoArgsConstructor
@Getter
public class JoinCourseRequest extends BasicRequest {
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "crs_id", referencedColumnName = "crs_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "crs_role_code", referencedColumnName = "crs_role_code")
    private CourseMemberRole role;

    public JoinCourseRequest(Member requester, Course course, CourseMemberRole role) {
        super(requester, LocalDateTime.now());
        this.course = course;
        this.role = role;
    }
}
