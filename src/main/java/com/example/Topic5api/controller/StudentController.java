package com.example.Topic5api.controller;

import com.example.Topic5api.entity.Student;
import com.example.Topic5api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<?> create (@RequestBody Student student){
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read (@PathVariable (value = "id") Long studentId){
        Optional<Student> oStudent = studentService.findById(studentId);

        if(!oStudent.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Student studentDetails, @PathVariable(value = "id") Long studentId){
        Optional<Student> student = studentService.findById(studentId);

        if(!student.isPresent()){
            return ResponseEntity.notFound().build();
        }

        student.get().setName(studentDetails.getName());
        student.get().setLastname(studentDetails.getLastname());
        student.get().setEmail(studentDetails.getEmail());
        student.get().setEnable(studentDetails.getEnable());

        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(student.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable(value = "id") Long studentId){

        if(!studentService.findById(studentId).isPresent()){
            return ResponseEntity.notFound().build();
        }
        studentService.deleteById(studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public List<Student> readAll(){
        List<Student> students = StreamSupport
                .stream(studentService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return students;
    }
}
