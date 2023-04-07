package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NoStudentsFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        Double averageAgeOfStudents = studentRepository.findAll().stream()
                .mapToDouble(Student::getAge).average().orElseThrow(NoStudentsFoundException::new);
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

    public Collection<Student> findStudentsStartsNameWith(String letter) {
        logger.info("Was invoked method for get students whose name begins with {}", letter);
        List<Student> collect = studentRepository.findAll().stream()
                .filter(a -> a.getName().startsWith(letter))
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
        if (collect.isEmpty()) {
            logger.warn("No students found");
        } else {
            logger.debug("Students whose name begins with {} were shown", letter);
        }
        return collect;
    }

    public void printNamesIn3Thread() {
        printListOfNames();

        System.out.println(getNameByIndex(0));
        System.out.println(getNameByIndex(1));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " " + getNameByIndex(2));
            System.out.println(Thread.currentThread().getName() + " " + getNameByIndex(3));
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " " + getNameByIndex(4));
            System.out.println(Thread.currentThread().getName() + " " + getNameByIndex(5));
        }).start();
    }

    public void printNamesIn1Thread() {
        printListOfNames();

        printNameByIndex(0);
        printNameByIndex(1);

        new Thread(() -> {
            printNameByIndex(2);
            printNameByIndex(3);
        }).start();

        new Thread(() -> {
            printNameByIndex(4);
            printNameByIndex(5);
        }).start();
    }

    private List<String> getNamesList() {
        return getAllStudents().stream().map(Student::getName).toList();
    }

    private void printListOfNames() {
        System.out.println(getNamesList());
    }

    private String getNameByIndex(int index) {
        return getNamesList()
                .stream()
                .skip(index)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Index out of bounds"));
    }

    int count = 0;

    private synchronized void printNameByIndex(int index) {
        String name = getNamesList()
                .stream()
                .skip(index)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Index out of bounds"));
        System.out.println(count + " " + name);
        count++;
    }
}