package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.LongStream;

@Service
public class InfoService {

    @Value("${server.port}")
    private Integer port;

    public String getPort() {
        return "The application is running on port: " + port;
    }

    public long getSum() {
        long startTime = System.currentTimeMillis();
//        long sum = Stream.iterate(1L, a -> a + 1)
//                .limit(1_000_000)
//                .reduce(0L, (a, b) -> a + b);
        long sum = LongStream.rangeClosed(1, 1_000_000)
                .parallel()
                .sum();
        long endTime = System.currentTimeMillis();
        System.out.println("Время выполнения: " + (endTime - startTime) + " мс");
        return sum;
    }
}
