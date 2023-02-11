package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = StudentController.class)
public class StudentControllerWithMockTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    Long id = 1L;
    private final String name = "Test";
    private final int age = 15;

    JSONObject studentObject = new JSONObject();
    Student student = new Student();
    ObjectMapper mapper = new ObjectMapper();
    List<Student> studentList = List.of(student);

    @BeforeEach
    void setUp() {
        studentObject.put("name", name);
        studentObject.put("age", age);

        student.setId(id);
        student.setName(name);
        student.setAge(age);

        student.setFaculty(new Faculty());
    }

    @Test
    public void createStudent() throws Exception {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void readStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .content(id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void updateStudent() throws Exception {
        when(studentRepository.existsById(any(Long.class))).thenReturn(true);
        String newName = "Tester2";
        student.setName(newName);
        studentObject.put("name", newName);
        studentObject.put("id", id);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void deleteStudents() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id)
                        .content(id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void readAllStudents() throws Exception {
        when(studentRepository.findAll()).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/all")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(studentList)));
    }

    @Test
    public void readStudentsByAge() throws Exception {
        when(studentRepository.findByAge(age)).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filter/" + age)
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(studentList)));
    }

    @Test
    public void readStudentsBetweenAge() throws Exception {
        when(studentRepository.findByAgeBetween(10, 20)).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filter/" + 10 + "/" + 20)
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(studentList)));
    }

    @Test
    public void readFacultyByStudent() throws Exception {
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/faculty/" + id)
                        .content(id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}


