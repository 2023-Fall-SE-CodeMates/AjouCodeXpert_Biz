package codemates.ajoucodexpert.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

/**
 * 사용자가 특정 기능 수행 시 관리자 및 다른 사용자에게 요청하는 객체
 * 요청자 ID, 요청 시간, 요청 상태, 처리완료 시간을 가짐
 */
@Entity
@RequiredArgsConstructor
@Getter @Setter
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UserRequest extends BaseEntity {
    final static int unprocessed = 0;
    final static int accepted = 1;
    final static int rejected = -1;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "request_time")
    private LocalDateTime requestTime;
    @Column(name = "request_status")
    private Integer requestStatus;
    @Column(name = "complete_time")
    private LocalDateTime completeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "requester_id", referencedColumnName = "id")
    private Member requester;

    public UserRequest(Member requester, LocalDateTime requestTime, int i, LocalDateTime completeTime) {
        this.requester = requester;
        this.requestTime = requestTime;
        this.requestStatus = i;
        this.completeTime = completeTime;
    }

    abstract void accept();

    abstract void reject();

    abstract void process();

}
