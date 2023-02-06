package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerWithMockTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;
    private final Long id = 1L;
    private final String name = "Test";
    private final String color = "white";

    JSONObject facultyObject = new JSONObject();
    Faculty faculty = new Faculty();

    ObjectMapper mapper = new ObjectMapper();
    List<Faculty> facultyList = List.of(faculty);

    @BeforeEach
    void setUp() {
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
    }

    @Test
    public void createFaculty() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void readFaculty() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .content(id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void updateFaculty() throws Exception {
        when(facultyRepository.existsById(any(Long.class))).thenReturn(true);

        String newName = "tttt3";
        faculty.setName(newName);
        facultyObject.put("name", newName);
        facultyObject.put("id", id);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void deleteFaculty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void readAllFaculties() throws Exception {
        when(facultyRepository.findAll()).thenReturn(facultyList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(facultyList)));
    }

    @Test
    public void readFacultiesByColor() throws Exception {
        when(facultyRepository.findByColor(color)).thenReturn(facultyList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filter/" + color)
                        .content(color)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(facultyList)));
    }

    @Test
    public void readByNameOrColor() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setId(5L);
        faculty1.setName("mmm");
        faculty1.setColor("lll");
        List<Faculty> facultyList1 = List.of(faculty, faculty1);

        when(facultyRepository.findByColorIgnoreCase(any(String.class))).thenReturn(faculty);
//        when(facultyRepository.findByNameIgnoreCase(name)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filter", color)
                        .param("color", color)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(facultyList1)));
    }

    @Test
    public void readStudentsByFaculty() throws Exception {
        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/students/" + id)
                        .content(id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
