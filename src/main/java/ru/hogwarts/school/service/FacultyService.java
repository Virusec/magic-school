package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundMyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Invalid faculty id " + id));
                .orElseThrow(() -> new EntityNotFoundMyException("Invalid faculty id " + id));
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (facultyRepository.existsById(faculty.getId())) {
            return facultyRepository.save(faculty);
        }
        return null;
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    public Faculty getFacultyByName(String name) {
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public Faculty getFacultyByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }


    public Collection<Student> getStudentsByFaculty(Long id) {
        return facultyRepository.findById(id).map(Faculty::getStudents).orElse(null);
    }

}
