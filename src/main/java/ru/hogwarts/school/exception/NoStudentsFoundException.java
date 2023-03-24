package ru.hogwarts.school.exception;

public class NoStudentsFoundException extends RuntimeException {
    public NoStudentsFoundException() {
        super("No students found in the repository");
    }
}
