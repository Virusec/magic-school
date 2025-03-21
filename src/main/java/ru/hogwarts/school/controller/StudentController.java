package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student foundStudent = studentService.updateStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("all")
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/filter/starts-letter/{letter}")
    public ResponseEntity<Collection<String>> getStudentsStartsNameWith(@PathVariable String letter) {
        return ResponseEntity.ok(studentService.findStudentsStartsNameWith(letter));
    }

    @GetMapping("filter/{age}")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@PathVariable int age) {

        Collection<Student> existStudents = studentService.getStudentsByAge(age);
        if (existStudents == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(existStudents);
    }

    @GetMapping("filter/{min}/{max}")
    public ResponseEntity<Collection<Student>> getStudentsBetweenAge(@PathVariable int min, @PathVariable int max) {
        Collection<Student> existStudents = studentService.getStudentsByAgeBetween(min, max);
        if (existStudents == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(existStudents);
    }

    @GetMapping("faculty/{id}")
    public ResponseEntity<Faculty> getFacultyByStudentsId(@PathVariable Long id) {
        Faculty findFaculty = studentService.getFacultyByStudentId(id);
        if (findFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findFaculty);
    }

    @GetMapping("quantity")
    public Integer getCountOfStudents() {
        return studentService.getCountOfStudents();
    }

    @GetMapping("average")
    public Double getAverageAgeOfStudents() {
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("5-last-student")
    public Collection<Student> get5LastStudent() {
        return studentService.get5LastStudent();
    }

    @GetMapping("/print-names")
    public void printNames() {
        studentService.printNames();
    }

    @GetMapping("/print-names-Sync")
    public void printNamesSynchronized() {
        studentService.printNamesSynchronized();
    }
}