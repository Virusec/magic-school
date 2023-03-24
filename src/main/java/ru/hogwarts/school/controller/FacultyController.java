package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.findFaculty(id));
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.updateFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("all")
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @GetMapping("filter/{color}")
    public ResponseEntity<Collection<Faculty>> getFacultiesByColor(@PathVariable String color) {
        Collection<Faculty> existingFaculties = facultyService.getFacultiesByColor(color);
        if (existingFaculties == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(existingFaculties);
    }

    @GetMapping("filter")
    public ResponseEntity<Faculty> getFacultyByNameOrColor(@RequestParam(required = false) String name,
                                                           @RequestParam(required = false) String color) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.getFacultyByName(name));
        }
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.getFacultyByColor(color));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("students/{id}")
    public ResponseEntity<Collection<Student>> getStudentsByFacultyId(@PathVariable Long id) {
        Collection<Student> findFacultyStudents = facultyService.getStudentsByFaculty(id);
        if (findFacultyStudents == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findFacultyStudents);
    }

    @GetMapping("/long-title")
    public ResponseEntity<List<String>> getLongNameFaculty() {
        List<String> stringList = facultyService.getLongestNameFaculties();
        return ResponseEntity.ok(stringList);
    }
}