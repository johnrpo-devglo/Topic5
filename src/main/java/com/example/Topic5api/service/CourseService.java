package com.example.Topic5api.service;

import com.example.Topic5api.entity.Course;
import com.example.Topic5api.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CourseService {

    public Iterable<Course> findAll();

    public Page<Course> findAll(Pageable pageable);

    public Optional<Course> findById(Long id);

    public Course save(Course course);

    public void deleteById (Long id);
}
