package ru.pegov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SchedulingTasksApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateBaseBySchedule.class);

    public static void main(String[] args) {
        SpringApplication.run(SchedulingTasksApplication.class);
        LOGGER.info("Updater is run");
    }
}
