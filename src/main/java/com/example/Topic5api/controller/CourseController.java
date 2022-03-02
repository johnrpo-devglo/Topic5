package com.example.Topic5api.controller;

import com.example.Topic5api.entity.Course;
import com.example.Topic5api.entity.Student;
import com.example.Topic5api.service.CourseService;
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
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;


    @PostMapping
    public ResponseEntity<?> create (@RequestBody Course course){
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read (@PathVariable(value = "id") Long courseId){
        Optional<Course> oCourse = courseService.findById(courseId);

        if (!oCourse.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oCourse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Course courseDetails, @PathVariable(value = "id") Long courseId){
        Optional<Course> course = courseService.findById(courseId);

        if(!course.isPresent()){
            return ResponseEntity.notFound().build();
        }

        course.get().setNameCourse(courseDetails.getNameCourse());
        course.get().setDuration(courseDetails.getDuration());

        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable(value = "id") Long courseId){

        if(!courseService.findById(courseId).isPresent()){
            return ResponseEntity.notFound().build();
        }
        courseService.deleteById(courseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public List<Course> readAll(){
        List<Course> courses = StreamSupport
                .stream(courseService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return courses;
    }

    @PutMapping("/{courseId}/students/{studentId}")
    Course enrolledStudentU(
            @PathVariable Long courseId,
            @PathVariable Long studentId
    ){
        Course course = courseService.findById(courseId).get();
        Student student = studentService.findById(studentId).get();
        course.enrollStudent(student);
        return courseService.save(course);
    }

}
