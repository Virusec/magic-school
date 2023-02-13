package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

//@Repository           //если создан свой репозиторий
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer getCountOfStudents();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Double getAverageAgeOfStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> get5LastStudent();
}
