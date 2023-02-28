package com.example.demo.Course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Course.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
	
	List<Course> findCoursesByProfessorsId(Long professorId);
	List<Course> findCourseByName(String name);
	List<Course> findCourseBySemester(Integer semester);
}
