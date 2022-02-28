package com.example.Topic5api.controller;

import com.example.Topic5api.entity.Course;
import com.example.Topic5api.entity.Student;
import com.example.Topic5api.service.CourseService;
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

}
