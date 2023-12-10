package codemates.ajoucodexpert.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "OpenClassRequest")
@AllArgsConstructor @NoArgsConstructor
@Getter
public class OpenClassRequest extends AdminRequest{
    private String courseCode;
    private String courseName;

    public OpenClassRequest(Member requester, String courseCode, String courseName) {
        super(requester, LocalDateTime.now());
        this.courseCode = courseCode;
        this.courseName = courseName;
    }
}
