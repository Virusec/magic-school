package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColor(String color);

    Faculty findByNameIgnoreCase(String name);

    Faculty findByColorIgnoreCase(String color);

    boolean existsByName(String name);
}
