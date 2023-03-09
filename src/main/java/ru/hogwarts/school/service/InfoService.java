package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoService {

    @Value("${server.port}")
    private Integer port;

    public String getPort() {
        return "The application is running on port: " + port;
    }
}
