//package ru.hogwarts.school.controller;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import ru.hogwarts.school.model.Student;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Transactional
//public class StudentControllerTest {
//    @LocalServerPort
//    private int port;
//    @Autowired
//    private StudentController studentController;
//    @Autowired
//    private TestRestTemplate restTemplate;
//    @Test
//    public void testPostStudent() throws Exception {
//        Student student = new Student();
//        student.setId(1L);
//        student.setName("Vasiliy");
//        student.setAge(22);
//
//        Assertions.assertThat(this.restTemplate
//                        .postForObject("http://localhost:" + port + "/student", student, String.class))
//                .isNotEmpty();
//    }
//
//    @Test
//    public void testGetStudents() throws Exception {
//        Assertions.assertThat(this.restTemplate
//                        .getForObject("http://localhost:"
//                                        + port
//                                        + "/student"
//                                , String.class))
//                .isNotEmpty();
//    }
//
//    @Test
//    public void testPutStudent() throws Exception {
//        Student student = new Student();
//        student.setId(1L);
//        student.setName("Tim");
//        student.setAge(22);
//        restTemplate.put("http://localhost:" + port + "/student", student, Student.class);
//        Assertions.assertThat(this.restTemplate
//                        .getForObject("http://localhost:"
//                                        + port
//                                        + "/student/1"
//                                , Student.class))
//                .isEqualTo(student);
//    }
//
//    @Test
//    void testGetStudentsById() throws Exception {
//        Assertions
//                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + 1, Student.class))
//                .isNotNull();
//    }
//
//    @Test
//    void testRemoveStudent() {
//        Student student = new Student();
//        student.setId(2L);
//        student.setName("Sam");
//        restTemplate.put("http://localhost:" + port + "/student", student, Student.class);
//        restTemplate.delete("http://localhost:" + port + "/student/" + 2, Student.class);
//        Assertions
//                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + 2, Student.class))
//                .isNull();
//    }
//
//
//    @Test
//    void testGetStudentsByAgeBetween() throws Exception {
//        Student student = new Student();
//        student.setId(2L);
//        student.setName("John");
//        student.setAge(2);
//
//        restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);
//        Assertions
//                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/filter", Student.class))
//                .isNotNull();
//    }
//}
