package codemates.ajoucodexpert.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Entity
@Table(name = "TestCase")
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TestCase {
    @Embeddable
    @Getter @Setter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class TestCaseId implements Serializable {
        @Column(name = "crs_id")
        private Long courseId;
        @Column(name = "hw_idx")
        private Long homeworkIdx;
        @Column(name = "prob_idx")
        private Long problemIdx;
        @Column(name = "tc_idx")
        private Long testCaseIdx;
    }

    @EmbeddedId
    private TestCaseId id;

    @MapsId("problemId")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumns({
            @JoinColumn(name = "crs_id", referencedColumnName = "crs_id"),
            @JoinColumn(name = "hw_idx", referencedColumnName = "hw_idx"),
            @JoinColumn(name = "prob_idx", referencedColumnName = "prob_idx")
    })
    private Problem problem;

    @Column(name = "tc_input", length = 2000)
    private String input;
    @Column(name = "tc_output", length = 2000)
    private String output;

}
