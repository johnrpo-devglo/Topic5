package com.example.Topic5api.service;

import com.example.Topic5api.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StudentService {

    public Iterable<Student> findAll();

    public Page<Student> findAll(Pageable pageable);

    public Optional<Student> findById(Long id);

    public Student save(Student student);

    public void deleteById(Long id);
}
