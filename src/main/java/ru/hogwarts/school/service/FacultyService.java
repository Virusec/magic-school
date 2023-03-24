package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundMyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        Faculty save = facultyRepository.save(faculty);
        logger.debug("Faculty with id = {} has been created", faculty.getId());
        return save;
    }

    public Faculty findFaculty(long id) {
        logger.info("Was invoked method for find faculty");
        return facultyRepository
                .findById(id)
                .map(faculty -> {
                    logger.debug("Faculty with id = {} found", id);
                    return faculty;
                })
                .orElseThrow(() -> {
                    logger.warn("Faculty with id = {} not found", id);
                    return new EntityNotFoundMyException("Invalid faculty id = " + id);
                });
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty");
        Long id = faculty.getId();
        if (facultyRepository.existsById(id)) {
            logger.debug("Faculty with id = {} has been updated", id);
            return facultyRepository.save(faculty);
        }
        logger.warn("There is no faculty with id = {}", id);
        throw new IllegalArgumentException("Faculty was not updated");
    }

    public void deleteFaculty(long id) {
        logger.info("Was invoked method for delete faculty");
        if (facultyRepository.existsById(id)) {
            facultyRepository.deleteById(id);
            logger.debug("Faculty with id = {} has been deleted", id);
        } else {
            logger.warn("There is no faculty with id = {}", id);
            throw new IllegalArgumentException("Faculty with id = " + id + " not found");
        }
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for get faculties");
        List<Faculty> faculties = facultyRepository.findAll();
        if (!faculties.isEmpty()) {
            logger.debug("Faculties are displaying");
        } else {
            logger.warn("No faculties found");
        }
        return faculties;
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        logger.info("Was invoked method for get faculties by color");
        List<Faculty> byColor = facultyRepository.findByColor(color);
        if (!byColor.isEmpty()) {
            logger.debug("Faculties by color = {} were displayed", color);
            return byColor;
        } else {
            logger.warn("There is no faculty with color = {}", color);
            throw new IllegalArgumentException("Faculties with color = " + color + " not found");
        }
    }

    public Faculty getFacultyByName(String name) {
        logger.info("Was invoked method for get faculties by name");
        Optional<Faculty> optionalFaculty = Optional.ofNullable(facultyRepository.findByNameIgnoreCase(name));
        if (optionalFaculty.isPresent()) {
            Faculty byName = optionalFaculty.get();
            logger.debug("Faculty by name = {} was displayed", name);
            return byName;
        } else {
            logger.warn("There is no faculty with name = {}", name);
            throw new IllegalArgumentException("Faculties with name = " + name + " not found");
        }
    }

    public Faculty getFacultyByColor(String color) {
        logger.info("Was invoked method for get faculties by color");
        Optional<Faculty> optionalFaculty = Optional.ofNullable(facultyRepository.findByColorIgnoreCase(color));
        if (optionalFaculty.isPresent()) {
            Faculty byColor = optionalFaculty.get();
            logger.debug("Faculty with color = {} was displayed", color);
            return byColor;
        } else {
            logger.warn("There is no faculty with color = {}", color);
            throw new IllegalArgumentException("Faculties with color = " + color + " not found");
        }
    }


    public Collection<Student> getStudentsByFaculty(Long id) {
        logger.info("Was invoked method for get students by faculty id");
        return facultyRepository.findById(id)
                .map(faculty -> {
                    logger.debug("Students by faculty id = {} found", id);
                    return faculty.getStudents();
                })
                .orElseThrow(() -> {
                    logger.error("There is no faculty with id = {}", id);
                    return new IllegalArgumentException("Faculty with id = " + id + " not found");
                });
    }

    public String getLongestNameFaculty() {
        logger.info("Was invoked method for get the longest faculty name");
        Optional<Faculty> longestName = facultyRepository.findAll()
                .stream()
                .max(Comparator.comparingInt(a -> a.getName().length()));
        return longestName
                .map(faculty -> {
                    String name = faculty.getName();
                    logger.info("The longest faculty name is {}", name);
                    return name;
                })
                .orElseThrow(() -> {
                    logger.error("Faculty repository is empty");
                    throw new NoSuchElementException("Faculty repository is empty");
                });
    }

    public List<String> getLongestNameFaculties() {
        logger.info("Was invoked method for get the longest faculty name");
        List<Faculty> faculties = facultyRepository.findAll();
        if (faculties.isEmpty()) {
            logger.warn("Faculty repository is empty");
            return Collections.emptyList();
        }
        OptionalInt maxLength = faculties.stream()
                .mapToInt(a -> a.getName().length())
                .max();

        List<String> longestNames = faculties.stream()
                .map(Faculty::getName)
                .filter(name -> name.length() == maxLength.getAsInt())
                .toList();
        if (longestNames.size() == 1) {
            logger.info("The longest faculty name is {}", longestNames.get(0));
        } else {
            logger.info("Multiple faculties found with longest name: {}", longestNames);
        }
        return longestNames;
    }

}