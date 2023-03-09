package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        Student createdStudent = studentRepository.save(student);
        logger.debug("Student with id = {} has been created", createdStudent.getId());
        return createdStudent;
    }

    public Student findStudent(long id) {
        logger.info("Was invoked method for create student");
        return studentRepository.findById(id)
                .map(student -> {
                    logger.debug("Student with id = {} found", id);
                    return student;
                })
                .orElseThrow(() -> {
                    logger.warn("There is no student with id = {}", id);
                    return new IllegalArgumentException("Student with id = " + id + " not found");
                });
    }

    public Student updateStudent(Student student) {
        logger.info("Was invoked method for update student");
        Long id = student.getId();
        if (studentRepository.existsById(id)) {
            logger.debug("Student with id = {} has been updated", id);
            return studentRepository.save(student);
        }
        logger.error("There is no student with id = {}", id);
        throw new IllegalArgumentException("Student was not updated");
    }

    public void deleteStudent(long id) {
        logger.info("Was invoked method for delete student");
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            logger.debug("Student with id = {} has been deleted", id);
        } else {
            logger.error("There is no student with id = {}", id);
            throw new IllegalArgumentException("Student with id = " + id + " not found");
        }
    }

    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for get all students");
        List<Student> students = studentRepository.findAll();
        if (!students.isEmpty()) {
            logger.debug("Students are displaying");
        } else {
            logger.warn("No students found");
        }
        return students;
    }

    public Collection<Student> getStudentsByAge(int age) {
        logger.info("Was invoked method for get students by age");
        List<Student> students = studentRepository.findByAge(age);
        if (students.isEmpty()) {
            logger.warn("No students found");
        } else {
            logger.debug("Students by age = {} were displayed", age);
        }
        return students;
    }

    public Collection<Student> getStudentsByAgeBetween(int min, int max) {
        logger.info("Was invoked method for get students by age between");
        Collection<Student> students = studentRepository.findByAgeBetween(min, max);
        if (students.isEmpty()) {
            logger.warn("No students found");
        } else {
            logger.debug("Students by age between = {} and = {} were displayed", min, max);
        }
        return students;
    }

    public Faculty getFacultyByStudentId(long id) {
        logger.info("Was invoked method for get faculty by student id");
        return studentRepository.findById(id)
                .map(student ->
                {
                    logger.debug("Faculty by student id = {} found", id);
                    return student.getFaculty();
                })
                .orElseThrow(() -> {
                    logger.error("There is no student with id = {}", id);
                    return new IllegalArgumentException("Student not found");
                });
    }

    public Integer getCountOfStudents() {
        logger.info("Was invoked method for get count of students");
        Integer countOfStudents = studentRepository.getCountOfStudents();
        if (countOfStudents.describeConstable().isEmpty()) {
            logger.warn("No students found");
        } else {
            logger.debug("The number of students is = {}", countOfStudents);
        }
        return countOfStudents;
    }

    public Double getAverageAgeOfStudents() {
        logger.info("Was invoked method for get average age of students");
        Double averageAgeOfStudents = studentRepository.getAverageAgeOfStudents();
        if (averageAgeOfStudents.describeConstable().isEmpty()) {
            logger.warn("No students found");
        } else {
            logger.debug("Average age of students is = {}", averageAgeOfStudents);
        }
        return averageAgeOfStudents;
    }

    public Collection<Student> get5LastStudent() {
        logger.info("Was invoked method for get 5 last students");
        List<Student> lastStudents = studentRepository.get5LastStudent();
        if (lastStudents.isEmpty()) {
            logger.warn("No students found");
        } else {
            logger.debug("Five last students were displayed");
        }
        return lastStudents;
    }
}