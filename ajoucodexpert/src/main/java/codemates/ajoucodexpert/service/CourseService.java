package codemates.ajoucodexpert.service;

import codemates.ajoucodexpert.domain.Course;

import java.util.List;

public interface CourseService {
    Course createCourse(Course newCourse);
    Course getCourse(Long courseId);
    Course getCourse(String joinCode);
}
