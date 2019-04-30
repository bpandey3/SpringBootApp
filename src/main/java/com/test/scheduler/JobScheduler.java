package com.test.scheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.test.controller.FileController;

@Component
public class JobScheduler {
	
	private final static Logger LOGGER = LogManager.getLogger(FileController.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(fixedRate = 3600000)
    public void scheduleTaskWithFixedRate() {
    	LOGGER.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
    	
    	//fetch all files with lastmodified date with one hour to send alerts for the new files
    }

}
