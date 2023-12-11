package codemates.ajoucodexpert.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Entity
@Table(name = "Problem")
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Problem {
    @Embeddable
    @Getter @Setter
    @AllArgsConstructor @NoArgsConstructor
    public static class ProblemId implements Serializable {
        @Column(name = "crs_id")
        private Long courseId;
        @Column(name = "hw_idx")
        private Long homeworkIdx;
        @Column(name = "prob_idx")
        private Long problemIdx;
    }

    @EmbeddedId
    private ProblemId id;

    @Column(name = "prob_title", length = 50)
    private String title;

    @Column(name = "prob_desc", length = 2000)
    private String description;

    @Column(name = "prob_score")
    private Integer score;

    @Column(name = "prob_lang")
    private Integer language;

    @MapsId("homeworkId")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumns({
            @JoinColumn(name = "crs_id", referencedColumnName = "crs_id"),
            @JoinColumn(name = "hw_idx", referencedColumnName = "hw_idx")
    })
    private Homework homework;
}
