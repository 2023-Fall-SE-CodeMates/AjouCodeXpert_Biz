package codemates.ajoucodexpert.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Homework")
@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Homework {
    @Embeddable
    @Getter
    @AllArgsConstructor @NoArgsConstructor
    public static class HomeworkId implements Serializable {
        @Column(name = "crs_id")
        private Long courseId;
        @Column(name = "hw_idx")
        private Long homeworkIdx;
    }

    @EmbeddedId
    private HomeworkId id;

    @Column(name = "hw_title", length = 50)
    private String title;

    @Column(name = "hw_content", length = 2000)
    private String content;

    @Column(name = "hw_ed_date")
    private LocalDateTime endDate;

    @Column
    private Integer totalScore;

    @MapsId("courseId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crs_id", referencedColumnName = "crs_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;
}
