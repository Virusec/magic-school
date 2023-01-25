package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColor(String color);

    Faculty findByNameIgnoreCase(String name);

    Faculty findByColorIgnoreCase(String color);

    Faculty findFacultyByStudentsId(Long id);
}
